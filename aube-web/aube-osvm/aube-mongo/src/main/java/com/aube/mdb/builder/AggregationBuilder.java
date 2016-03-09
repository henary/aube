package com.aube.mdb.builder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.aube.mdb.operation.Expression;
import com.aube.mdb.operation.Projection;
import com.aube.mdb.result.FindRes;
import com.mongodb.client.MongoDatabase;

public class AggregationBuilder {

	public static enum AggregationModel {
		COUNT("$sum"), SUM("$sum"), AVG("$avg"), LAST("$last"), FIRST("$first"), MAX("$max"), MIN("$min"), DISTINCT("");

		private String optTag = null;

		private AggregationModel(String opt) {
			this.optTag = opt;
		}

		public String optTag() {
			return this.optTag;
		}
	};

	protected List<Bson> pipeLine = new ArrayList<>();
	protected Bson ids = null;
	protected String collectionName = null;

	public AggregationBuilder(String collectionName) {
		this.collectionName = collectionName;
	}

	public AggregationBuilder addCondition2Pipeline(Expression queryExpre) {
		pipeLine.add(new Document("$match", queryExpre.toBson()));
		return this;
	}

	public AggregationBuilder addSort2PipeLine(String field, boolean asc) {
		pipeLine.add(new Document("$sort", new Document(field, asc ? 1 : -1)));
		return this;
	}

	public AggregationBuilder addSort2PipeLine(String[] fields, boolean[] ascs) {
		Document sortDoc = new Document();
		for (int i = 0; i < fields.length; i++) {
			sortDoc.put(fields[i], ascs[i] ? 1 : -1);
		}

		pipeLine.add(new Document("$sort", sortDoc));
		return this;
	}

	public AggregationBuilder addLimit2PipeLine(Integer from, Integer size) {
		if (from != null) {
			pipeLine.add(new Document("$skip", from));
		}
		if (size != null) {
			pipeLine.add(new Document("$limit", size));
		}
		return this;
	}

	public AggregationBuilder addProjection2PipeLine(Projection projection) {
		pipeLine.add(new Document("$project", projection.toBson()));
		return this;
	}

	/**
	 * ������Ҫͳ�Ƶ��ֶΣ�
	 * 
	 * @param model
	 * @param calField
	 * @param fName
	 * @param groupFields
	 * @return
	 */
	public AggregationBuilder addGroup2PipeLine(String calField, String fName, AggregationModel model,
			String... groupFields) {
		Document doc = new Document();

		if (model != null) {
			switch (model) {
			case COUNT:
				doc.put(fName, new Document(model.optTag, 1));
				break;
			case DISTINCT:
				break;
			default:
				doc.put(fName, new Document(model.optTag, "$" + calField));
			}
		}

		if (groupFields.length > 0) {
			if (groupFields.length == 1)
				doc.put("_id", "$" + groupFields[0]);
			else {
				Document gr = new Document();
				for (String gf : groupFields) {
					gr.put(gf, "$" + gf);
				}
				doc.put("_id", gr);
			}
		} else {
			doc.put("_id", null);
		}
		this.pipeLine.add(new Document("$group", doc));
		return this;
	}

	/**
	 * 
	 * @param model
	 * @param calField
	 * @param fName
	 * @param groupFields
	 * @return
	 */
	public AggregationBuilder addGroup2PipeLine(AggregationModel model, String calField, String... groupFields) {
		return addGroup2PipeLine(calField, model.name().toLowerCase(), model, groupFields);
	}

	public AggregationBuilder addUnwindArray2PipeLine(String arrayFieldName) {
		this.pipeLine.add(new Document("$unwind", arrayFieldName));
		return this;
	}

	public AggregationBuilder setOut(String targetCollectionName) {
		this.pipeLine.add(new Document("$out", targetCollectionName));
		return this;
	}

	public FindRes<Object> group(MongoDatabase database) {
		Iterator<Document> result = database.getCollection(collectionName).aggregate(this.pipeLine).iterator();
		return new FindRes<Object>(result);
	}
}
