package br.com.sisnema.financeiroweb.negocio;

import java.util.List;

import org.hibernate.boot.model.source.internal.hbm.ModelBinder;

import br.com.sisnema.financeiroweb.dao.UsuarioDAO;
import br.com.sisnema.financeiroweb.exception.DAOException;
import br.com.sisnema.financeiroweb.exception.RNException;
import br.com.sisnema.financeiroweb.model.Usuario;

public class UsuarioRN extends RN<Usuario> {

	public UsuarioRN() {
		super(new UsuarioDAO());
	}

	public void salvar(Usuario model) throws RNException {
		if (model.getCodigo() != null) {
			try {
				UsuarioDAO uDAO = (UsuarioDAO) dao;
				uDAO.alterar(model);
			} catch (DAOException e) {
				throw new RNException(e.getMessage(), e);
			}
		} else {
			try {
				Usuario userExistente = buscarUsuarioPorLogin(model.getLogin());
				if (userExistente != null) {
					throw new RNException("J� existe um usu�rio com o login informado");
				}
				dao.salvar(model);
			} catch (DAOException e) {
				throw new RNException(e.getMessage(), e);
			}
		}

	}

	public void excluir(Usuario model) throws RNException {
		try {
			dao.excluir(model);
		} catch (DAOException e) {
			throw new RNException(e.getMessage(), e);
		}
	}

	public Usuario obterPorId(Usuario filtro) {
		return dao.obterPorId(filtro);
	}

	public List<Usuario> pesquisar(Usuario filtros) {
		return dao.pesquisar(filtros);
	}

	public Usuario buscarUsuarioPorSenhaELogin(String login, String senha) {
		return ((UsuarioDAO) dao).buscarUsuarioPorLoginEsenha(login, senha);
	}

	public Usuario buscarUsuarioPorLogin(String login) {
		return ((UsuarioDAO) dao).buscarUsuarioPorLogin(login);
	}

}
