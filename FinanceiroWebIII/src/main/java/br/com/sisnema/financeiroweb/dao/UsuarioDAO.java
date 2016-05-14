package br.com.sisnema.financeiroweb.dao;

import java.util.List;

import javax.sql.rowset.FilteredRowSet;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.sisnema.financeiroweb.exception.DAOException;
import br.com.sisnema.financeiroweb.model.Usuario;

public class UsuarioDAO extends DAO<Usuario> {

	public void salvar(Usuario model) throws DAOException {
		try {
			getSession().save(model);
		} catch (Exception e) {
			throw new DAOException("Não foi possível inserir o usuário. Erro: " + e.getMessage(), e);
		}
	}

	public void alterar(Usuario model) throws DAOException {
		try {
			getSession().update(model);
		} catch (Exception e) {
			throw new DAOException("Não foi possível alterar o usuário. Erro: " + e.getMessage(), e);
		}
	}

	public void excluir(Usuario model) throws DAOException {
		try {
			getSession().delete(model);
		} catch (Exception e) {
			throw new DAOException("Não foi possível excluir o usuário. Erro: " + e.getMessage(), e);
		}
	}

	public Usuario obterPorId(Usuario filtro) {
		return getSession().get(Usuario.class, filtro.getCodigo());
	}

	public List<Usuario> pesquisar(Usuario filtros) {
		// Definimos a criteria basica (select + from Usuario)
		Criteria criteria = getSession().createCriteria(Usuario.class);
		if (StringUtils.isNoneBlank(filtros.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtros.getNome(), MatchMode.ANYWHERE));
		}
		if (filtros.getNascimento() != null) {
			criteria.add(Restrictions.eq("nascimento", filtros.getNascimento()));
		}
		criteria.addOrder(Order.asc("nome"));
		return criteria.list();
	}

	public Usuario buscarUsuarioPorLoginEsenha(String login, String senha) {
		Criteria criteria = getSession().createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("login", login));
		criteria.add(Restrictions.eq("senha", login));
		return (Usuario) criteria.uniqueResult();
	}

	public Usuario buscarUsuarioPorLogin(String login) {
		Criteria criteria = getSession().createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("senha", login));
		return (Usuario) criteria.uniqueResult();
	}

}
