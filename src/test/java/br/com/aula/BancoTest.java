package br.com.aula;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import br.com.aula.exception.*;
import org.junit.Assert;
import org.junit.Test;

public class BancoTest {

	//A.1
	@Test
	public void deveCadastrarConta() throws ContaJaExistenteException,ContaComNumeroInvalidoException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta conta = new Conta(cliente, 123, 0, TipoConta.CORRENTE);
		Banco banco = new Banco();

		// Ação
		banco.cadastrarConta(conta);

		// Verificação
		assertEquals(1, banco.obterContas().size());
	}

	//A.2
	@Test(expected = ContaJaExistenteException.class)
	public void naoDeveCadastrarContaNumeroRepetido() throws ContaJaExistenteException,ContaComNumeroInvalidoException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta conta1 = new Conta(cliente, 123, 0, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Maria");
		Conta conta2 = new Conta(cliente2, 123, 0, TipoConta.POUPANCA);

		Banco banco = new Banco();

		// Ação
		banco.cadastrarConta(conta1);
		banco.cadastrarConta(conta2);

		Assert.fail();
	}

	//A.3
	@Test(expected = ContaComNumeroInvalidoException.class)
	public void naoDeveCadastrarContaComNumeroInvalido() throws ContaComNumeroInvalidoException,ContaJaExistenteException {

		//Cenario
		Cliente cliente = new Cliente("John");
		Conta conta = new Conta(cliente, -1, 0, TipoConta.CORRENTE);
		Banco banco = new Banco();

		//Ação
		banco.cadastrarConta(conta);

		Assert.fail();
	}

	//A.4
	@Test(expected = ContaJaExistenteException.class)
	public void naoDeveCadastrarContaComNomeExistente() throws ContaJaExistenteException, ContaComNumeroInvalidoException {
		// Cenario
		Cliente cliente = new Cliente("John");
		Conta conta1 = new Conta(cliente, 523, 0, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("John");
		Conta conta2 = new Conta(cliente2, 343, 0, TipoConta.POUPANCA);

		Banco banco = new Banco();

		//Ação
		banco.cadastrarConta(conta1);
		banco.cadastrarConta(conta2);

		Assert.fail();
	}

	//B.1
	@Test
	public void deveEfetuarTransferenciaContasCorrentes() throws ContaSemSaldoException, ContaNaoExistenteException, TransferenciaComValorNegativoException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta contaOrigem = new Conta(cliente, 123, 0, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Maria");
		Conta contaDestino = new Conta(cliente2, 456, 0, TipoConta.CORRENTE);

		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));

		// Ação
		banco.efetuarTransferencia(123, 456, 100);

		// Verificação
		assertEquals(-100, contaOrigem.getSaldo());
		assertEquals(100, contaDestino.getSaldo());
	}

	//B.2 e B.4
	@Test (expected = ContaNaoExistenteException.class)
	public void deveVerificarSeContaExiste() throws ContaSemSaldoException, ContaNaoExistenteException, TransferenciaComValorNegativoException {
		Cliente cliente = new Cliente("John");
		Conta contaOrigem = new Conta(cliente,34,0, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Alan");
		Conta contaDestino = new Conta(cliente2,88,0,TipoConta.POUPANCA);

		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));

		//Ação
		banco.efetuarTransferencia(33,88,250);
		banco.efetuarTransferencia(34,87,250);

		Assert.fail();
	}

	//B.3
	@Test(expected = ContaSemSaldoException.class)
	public void contaPoupancaNaoDeveFicarNegativa() throws ContaSemSaldoException, ContaNaoExistenteException, TransferenciaComValorNegativoException{
		Cliente cliente = new Cliente("John");
		Conta contaOrigem = new Conta(cliente,34,0, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Alan");
		Conta contaDestino = new Conta(cliente2,88,0,TipoConta.POUPANCA);

		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));

		//Ação
		banco.efetuarTransferencia(88,34,250);

		Assert.fail();
	}

	//B.5
	@Test(expected = TransferenciaComValorNegativoException.class)
	public void naoDeveEfetuarTransferenciaComValorNegativo() throws ContaNaoExistenteException, ContaSemSaldoException, TransferenciaComValorNegativoException {
		Cliente cliente = new Cliente("John");
		Conta contaOrigem = new Conta(cliente,34,0, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Alan");
		Conta contaDestino = new Conta(cliente2,88,0,TipoConta.POUPANCA);

		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));

		//Ação
		banco.efetuarTransferencia(34,88,-20);

		Assert.fail();
	}


}
