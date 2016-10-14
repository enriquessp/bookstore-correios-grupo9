package br.unicamp.bookstore.uc16;

import br.unicamp.bookstore.dao.EnderecosDosCorreiosDao;

/**
 * Created by enrique1 on 10/4/16.
 */
public class BuscarEndereco {

    private final EnderecosDosCorreiosDao enderecosDosCorreiosDao;

    public BuscarEndereco(EnderecosDosCorreiosDao enderecosDosCorreiosDao) {
	this.enderecosDosCorreiosDao = enderecosDosCorreiosDao;
    }

    public String buscarEndereco(String cep) {
        return enderecosDosCorreiosDao.recuperaEndereco(cep);
    }

}