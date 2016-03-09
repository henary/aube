package com.aube.mdb.helper;

import com.aube.mdb.builder.AggregationBuilder;
import com.aube.mdb.builder.DeleteBuilder;
import com.aube.mdb.builder.DistinctBuilder;
import com.aube.mdb.builder.FindBuilder;
import com.aube.mdb.builder.IndexBuilder;
import com.aube.mdb.builder.InsertBuilder;
import com.aube.mdb.builder.UpdateBuilder;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class BuilderUtils {

	public static DistinctBuilder prepareDistinct(String collectionName) {
		return new DistinctBuilder(collectionName);
	}

	public static AggregationBuilder prepareAggregation(String collectionName) {
		return new AggregationBuilder(collectionName);
	}

	public static FindBuilder prepareFind(String collectionName) {
		return new FindBuilder(collectionName);
	}

	public static <T> FindBuilder<T> prepareFind(Class<T> pojoClass) {
		return new FindBuilder<T>(pojoClass);
	}

	public static <T> FindBuilder<T> prepareFind(String collectionName, Class<T> pojoClass) {
		return new FindBuilder<T>(collectionName, pojoClass);
	}

	public static UpdateBuilder prepareUpdate(String collectionName) {
		return new UpdateBuilder(collectionName);
	}

	public static <T> UpdateBuilder<T> prepareUpdate(Class<T> pojoClass) {
		return new UpdateBuilder<>(pojoClass);
	}

	public static InsertBuilder prepareInsert(String collectionName) {
		return new InsertBuilder<>(collectionName);
	}

	public static <T> InsertBuilder<T> prepareInsert(T pojo) {
		return new InsertBuilder<T>((Class<T>) pojo.getClass()).addData4Bean(pojo);
	}

	public static <T> InsertBuilder<T> prepareInsert(Class<T> pojoClass) {
		return new InsertBuilder<>(pojoClass);
	}

	public static DeleteBuilder prepareDelete(String collectionName) {
		return new DeleteBuilder(collectionName);
	}

	public static <T> DeleteBuilder prepareDelete(Class<T> pojoClass) {
		return new DeleteBuilder(pojoClass.getCanonicalName());
	}

	public static IndexBuilder prepareIndex(String collectionName) {
		return new IndexBuilder(collectionName);
	}

}
