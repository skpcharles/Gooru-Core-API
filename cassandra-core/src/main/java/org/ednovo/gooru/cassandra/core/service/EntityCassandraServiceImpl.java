/**
 * 
 */
package org.ednovo.gooru.cassandra.core.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.cassandra.core.dao.EntityCassandraDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.astyanax.model.ColumnList;

/**
 * @author SearchTeam
 * 
 */
public abstract class EntityCassandraServiceImpl<M extends Serializable> implements EntityCassandraService<String, M> {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(EntityCassandraServiceImpl.class);
	
	@Override
	public void delete(String id) {
		getCassandraDao().delete(id);
	}

	@Override
	public void delete(String... ids) {
		for (String id : ids) {
			delete(id);
		}
	}
	
	@Override
	public String get(String key,
			String column) {
		return getCassandraDao().read(key, column);
	}
	
	@Override
	public Integer getInt(String key, String column) {
		Integer value = 0;
		try {
		 Long viewL =  getCassandraDao().readAsLong(key, column);
		 value = Integer.parseInt(viewL + "");
		} catch (Exception e) { 
			LOGGER.error("parser error : " + e);
			try {
				value =  getCassandraDao().readAsInteger(key, column);
			} catch (Exception e1) { 
				LOGGER.error("parser error : " + e1);
				try {
					 String view =  getCassandraDao().read(key, column);
					 value = Integer.parseInt(view);
				} catch (Exception e2) {
					LOGGER.error("parser error : " + e2);
					
				}
			}	
		}
		return value;
	}

	@Override
	public M read(String key) {
		return getCassandraDao().read(key);
	}

	@Override
	public List<M> read(List<String> keys) {
		return getCassandraDao().read(keys);
	}

	@Override
	public void save(Collection<M> models,
			Collection<String> modelKeys) {
		getCassandraDao().save(models, modelKeys, false);

	}

	@Override
	public List<M> getAll() {
		return getCassandraDao().getAll();
	}

	@Override
	public void save(String key,
			Map<String, Object> entity,
			String prefix,
			boolean reset) {
		getCassandraDao().save(key, entity, prefix, reset);
	}

	protected abstract EntityCassandraDao<M> getCassandraDao();

	@Override
	public void save(M model) {
		getCassandraDao().save(model);
	}

	@Override
	public ColumnList<String> getColumns(String rowKey,
			Collection<String> fields){
		return getCassandraDao().getColumns(rowKey, fields);
	}
	
	@Override
	public Map<String, String> readViewsCount(String rowKeys) {
		return getCassandraDao().readViewsCount(rowKeys);
	}

	@Override
	public void updateViewsCount(Map<String, String> viewsData) {
		getCassandraDao().updateViewsCount(viewsData);
	}

	
}
