package br.com.cresol.desafio.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.cresol.desafio.dao.ClienteDAO;
import br.com.cresol.desafio.dao.ContratoDAO;
import br.com.cresol.desafio.dao.SimulacaoEmprestimoDAO;
import br.com.cresol.desafio.dto.Cliente;
import br.com.cresol.desafio.dto.Contrato;
import br.com.cresol.desafio.dto.ErrosResponse;
import br.com.cresol.desafio.dto.Parcela;
import br.com.cresol.desafio.dto.SimulacaoEmprestimo;
import br.com.cresol.desafio.dto.SimularEmprestimoPayload;
import br.com.cresol.desafio.dto.SimularEmprestimoResponse;
import br.com.cresol.desafio.util.JPAUtil;
import javassist.NotFoundException;

/**
 * @author evandro
 *
 */
public class EmprestimoService {

	private static final Double TAXA_MINIMA = 1.8;
	private static final Integer DOZE_PARCELAS = 12;
	private static final Integer NUMERO_MAXIMO_DE_PARCELAS = 12;
	private static final Long TRINTA_DIAS = 30L;
	
	private static EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

	/**
	 * @param payload
	 * @return
	 */
	public SimularEmprestimoResponse simular(SimularEmprestimoPayload payload) {				
		entityManager.getTransaction().begin();
		
		Cliente cliente = inicializarCliente(payload);		
		Contrato contrato = inicializarContrato(payload);
		
		SimulacaoEmprestimo simulacaoEmprestimo = new SimulacaoEmprestimo();
		simulacaoEmprestimo.setData(LocalDate.now());
		simulacaoEmprestimo.setDataValidade(simulacaoEmprestimo.getData().plusDays(TRINTA_DIAS));
		simulacaoEmprestimo.addContrato(contrato);

		cliente.addSimulacaoEmprestimo(simulacaoEmprestimo);
				
		entityManager.persist(cliente);
		entityManager.getTransaction().commit();

		return new SimularEmprestimoResponse(simulacaoEmprestimo);
	}

	/**
	 * @param payload
	 * @return
	 */
	private Contrato inicializarContrato(SimularEmprestimoPayload payload) {
		Contrato contrato = new Contrato();
		contrato.setNumero(gerarNumeroContrato());
		contrato.setTaxaJuros(obterTaxaJuros(payload.getValorContrato(), payload.getQuantidadeParcelas()));
		contrato.setParcelas(criarParcelas(payload, contrato));
		contrato.setValor(payload.getValorContrato());
		contrato.setQuantidadeParcelas(payload.getQuantidadeParcelas());
		return contrato;
	}

	/**
	 * @param payload
	 * @return
	 */
	private Cliente inicializarCliente(SimularEmprestimoPayload payload) {
		ClienteDAO clienteDAO = new ClienteDAO(entityManager);				
		Cliente cliente = clienteDAO.consultar(payload.getCpf());
		if(cliente == null) {
			cliente = new Cliente();
			cliente.setNome(payload.getNome());
			cliente.setCpf(payload.getCpf());
			cliente.setEmail(payload.getEmail());
		}
		return cliente;
	}

	/**
	 * @return
	 */
	private String gerarNumeroContrato() {
		String dataAtual = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		ContratoDAO contratoDAO = new ContratoDAO(entityManager);
		Contrato contrato = contratoDAO.consultarUltimoPorNumero(dataAtual);
		Integer sequencial = 0;
		if(contrato != null) {
			sequencial = new Integer(contrato.getNumero().substring(8));
		}		
		sequencial++;

		return dataAtual + String.format("%06d", sequencial);
	}

	/**
	 * @param valorContrato
	 * @param quantidadeParcela
	 * @return
	 */
	private Double obterTaxaJuros(BigDecimal valorContrato, Integer quantidadeParcela) {
		Double taxa = TAXA_MINIMA;
		if (valorContrato.compareTo(new BigDecimal(1000)) == 1) {
			taxa = 3.0;
		}

		if (quantidadeParcela > DOZE_PARCELAS) {
			taxa = +0.5;
		}

		return taxa;
	}

	/**
	 * @param valorContrato
	 * @param quantidadeParcela
	 * @param taxaJuros
	 * @return
	 */
	private BigDecimal calcularValorParcela(BigDecimal valorContrato, Integer quantidadeParcela, Double taxaJuros) {
		BigDecimal resultado = new BigDecimal(1 + (quantidadeParcela * taxaJuros)).setScale(1, BigDecimal.ROUND_DOWN);
		return valorContrato.multiply(resultado).divide(new BigDecimal(quantidadeParcela), 2, BigDecimal.ROUND_DOWN);
	}

	/**
	 * @param payload
	 * @param contrato
	 * @return
	 */
	private List<Parcela> criarParcelas(SimularEmprestimoPayload payload, Contrato contrato) {
		List<Parcela> parcelas = new ArrayList<>();
		Double taxa = obterTaxaJuros(payload.getValorContrato(), payload.getQuantidadeParcelas());

		for (int i = 0; i < payload.getQuantidadeParcelas(); i++) {
			Parcela parcela = new Parcela();

			parcela.setNumero(i + 1);
			parcela.setValor(calcularValorParcela(payload.getValorContrato(), payload.getQuantidadeParcelas(), taxa));
			parcela.setContrato(contrato);
			parcelas.add(parcela);
		}

		return parcelas;
	}
	
	/**
	 * @param payload
	 * @return
	 */
	public ErrosResponse validarCampos(SimularEmprestimoPayload payload) {
		ErrosResponse errosResponse = new ErrosResponse();
		validarEmail(errosResponse, payload.getEmail());
		validarCpf(errosResponse, payload.getCpf());
		validarNumeroParcelas(errosResponse, payload.getQuantidadeParcelas());
		
		return errosResponse;
	}

	/**
	 * @param errosResponse
	 * @param numeroParcelas
	 */
	private void validarNumeroParcelas(ErrosResponse errosResponse, Integer numeroParcelas) {
		if (numeroParcelas > NUMERO_MAXIMO_DE_PARCELAS) {
			errosResponse.add("Número de parcelas ultrapassou o permitido.");
		}
	}

	/**
	 * @param errosResponse
	 * @param cpf
	 */
	private void validarCpf(ErrosResponse errosResponse, String cpf) {
		CPFValidator cpfValidator = new CPFValidator();
		List<ValidationMessage> erros = cpfValidator.invalidMessagesFor(cpf);
		if (!erros.isEmpty()) {
			errosResponse.add("CPF Inválido");
		}
	}

	/**
	 * @param errosResponse
	 * @param email
	 */
	private void validarEmail(ErrosResponse errosResponse, String email) {
		if (!isValidEmailAddressRegex(email)) {
			errosResponse.add("Email Inválido");
		}
	}

	/**
	 * @param email
	 * @return
	 */
	private static boolean isValidEmailAddressRegex(String email) {
		boolean isEmailIdValid = false;
		if (email != null && email.length() > 0) {
			String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email);
			if (matcher.matches()) {
				isEmailIdValid = true;
			}
		}
		return isEmailIdValid;
	}
	
	/**
	 * @param numeroContrato
	 * @return
	 * @throws NotFoundException
	 */
	public SimularEmprestimoResponse consultar(String numeroContrato) throws NotFoundException {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		entityManager.getTransaction().begin();
		SimulacaoEmprestimoDAO simulacaoEmprestimoDAO = new SimulacaoEmprestimoDAO(entityManager);
		
		return new SimularEmprestimoResponse(simulacaoEmprestimoDAO.consultar(numeroContrato));
	}
	
	/**
	 * @param numeroContrato
	 * @throws NotFoundException
	 */
	public void excluir(String numeroContrato) throws NotFoundException {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		entityManager.getTransaction().begin();
		SimulacaoEmprestimoDAO simulacaoEmprestimoDAO = new SimulacaoEmprestimoDAO(entityManager);
		simulacaoEmprestimoDAO.excluir(numeroContrato);
		entityManager.getTransaction().commit();
	}
}
