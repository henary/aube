package com.aube.mdb.operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.bson.BsonDocument;
import org.bson.BsonDocumentWriter;
import org.bson.BsonType;
import org.bson.codecs.Encoder;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

import com.aube.util.Util4Script;
import com.mongodb.client.model.Filters;

@SuppressWarnings({"rawtypes","unchecked"})
public class Expression implements Util4Script{
	public static enum LogicRelate {
		AND, OR, NOT, NOR
	};

	/**
	 * @author Administrator
	 *
	 */
	public static enum OpenCloseState {
		
		/**
		 * [min,max)
		 */
		CO, 
		/**
		 *(min,max)
		 */
		OO, 
		/**
		 *(min,max]
		 */
		OC, 
		/**
		 *[min,max]
		 */
		CC
	};

	public final static String MongoDB_ID_FieldName = "_id";

	protected final LogicRelate logic;
	protected final List<Bson> condition = new ArrayList<Bson>();

	/**
	 * Ĭ��ΪAnd��ϵ��
	 */
	public Expression() {
		this(LogicRelate.AND);
	}

	/**
	 * ����ָ���ڸñ��ʽ�У�ÿ������֮��Ĺ�ϵ��
	 * 
	 * @param logic
	 */
	public Expression(LogicRelate logic) {
		this.logic = logic;
	}

	public Expression eq(final String fieldName, final Object value) {
		condition.add(Filters.eq(fieldName, value));
		return this;
	}

	public Expression ne(final String fieldName, final Object value) {
		condition.add(Filters.ne(fieldName, value));
		return this;
	}

	public Expression gt(final String fieldName, final Object value) {
		condition.add(Filters.gt(fieldName, value));
		return this;
	}

	public Expression lt(final String fieldName, final Object value) {
		condition.add(Filters.lt(fieldName, value));
		return this;
	}

	public Expression gte(final String fieldName, final Object value) {
		condition.add(Filters.gte(fieldName, value));
		return this;
	}

	public Expression lte(final String fieldName, final Object value) {
		condition.add(Filters.lte(fieldName, value));
		return this;
	}

	/**
	 * @param fieldName
	 * @param min
	 * @param max
	 * @param range
	 *           ����ָ����պ�״̬��
	 * @return
	 */
	public Expression between(final String fieldName, final Object min, final Object max, final OpenCloseState range) {
		condition.add(new Bson() {
			@Override
			public <TDocument> BsonDocument toBsonDocument(Class<TDocument> documentClass, CodecRegistry codecRegistry) {
				String from = null, to = null;
				switch (range) {
				case CO:
					from = "$gte";
					to = "$lt";
					break;
				case OO:
					from = "$gt";
					to = "$lt";
					break;
				case OC:
					from = "$gt";
					to = "$lte";
					break;
				case CC:
					from = "$gte";
					to = "$lte";
					break;
				}

				BsonDocumentWriter writer = new BsonDocumentWriter(new BsonDocument());
				writer.writeStartDocument();
				writer.writeName(fieldName);
				writer.writeStartDocument();
				writer.writeName(from);
				encodeValue(writer, min, codecRegistry);
				writer.writeName(to);
				encodeValue(writer, max, codecRegistry);
				writer.writeEndDocument();
				writer.writeEndDocument();

				return writer.getDocument();
			}
		});
		return this;
	}

	private static void encodeValue(final BsonDocumentWriter writer, final Object value, final CodecRegistry codecRegistry) {
		if (value == null) {
			writer.writeNull();
		} else if (value instanceof Bson) {
			((Encoder) codecRegistry.get(BsonDocument.class)).encode(writer, ((Bson) value).toBsonDocument(BsonDocument.class, codecRegistry), EncoderContext.builder().build());
		} else {
			((Encoder) codecRegistry.get(value.getClass())).encode(writer, value, EncoderContext.builder().build());
		}
	}

	public Expression in(final String fieldName, final Object[] values) {
		condition.add(Filters.in(fieldName, values));
		return this;
	}

	public Expression in(final String fieldName, final Iterable values) {
		condition.add(Filters.in(fieldName, values));
		return this;
	}

	public Expression nin(final String fieldName, final Object[] values) {
		condition.add(Filters.nin(fieldName, values));
		return this;
	}

	public Expression nin(final String fieldName, final Iterable values) {
		condition.add(Filters.nin(fieldName, values));
		return this;
	}

	public Expression exists(final String fieldName, final boolean exists) {
		condition.add(Filters.exists(fieldName, exists));
		return this;
	}

	public Expression type(final String fieldName, final BsonType type) {
		condition.add(Filters.type(fieldName, type));
		return this;
	}

	public Expression mod(final String fieldName, final long divisor, final long remainder) {
		condition.add(Filters.mod(fieldName, divisor, remainder));
		return this;
	}

	public Expression regex(final String fieldName, final String pattern) {
		condition.add(Filters.regex(fieldName, pattern));
		return this;
	}

	public Expression regex(final String fieldName, final String pattern, final String options) {
		condition.add(Filters.regex(fieldName, pattern, options));
		return this;
	}

	public Expression regex(final String fieldName, final Pattern pattern) {
		condition.add(Filters.regex(fieldName, pattern));
		return this;
	}

	public Expression text(final String search) {
		condition.add(Filters.text(search));
		return this;
	}

	public Expression where(final String javaScriptExpression) {
		condition.add(Filters.where(javaScriptExpression));
		return this;
	}

	public Expression all4Array(final String fieldName, final Object... values) {
		condition.add(Filters.all(fieldName, values));
		return this;
	}

	public Expression all4Array(final String fieldName, final Iterable values) {
		condition.add(Filters.all(fieldName, values));
		return this;
	}

	public Expression elemMatch4Array(final String fieldName, final Expression matchCondition) {
		condition.add(Filters.elemMatch(fieldName, matchCondition.toBson()));
		return this;
	}

	public Expression size4Array(final String fieldName, final int size) {
		condition.add(Filters.size(fieldName, size));
		return this;
	}

	/**
	 * ��������Ĳ�ѯ���ʽ ���������Ҫ��Ϊ�˼���3.0��ǰ��api�� ���Ǳ��������£��벻Ҫ���ø÷�����
	 * 
	 * @param bsons
	 * @return
	 */
	public Expression bson(Bson... bsons) {
		for (Bson bson : bsons) {
			condition.add(bson);
		}
		return this;
	}

	public Expression expression(Expression... expressions) {
		for (Expression expression : expressions) {
			condition.add(expression.toBson());
		}
		return this;
	}

	/**
	 * ��ָ��express ת��ΪBson����
	 * 
	 * @param matchCondition
	 * @return
	 */
	public Bson toBson() {
		if (!this.condition.isEmpty()) {
			Bson bson = null;
			if (this.condition.size() == 1) {
				bson = this.logic == LogicRelate.NOT ? Filters.not(this.condition.get(0)) : this.condition.get(0);
			} else {
				switch (this.logic) {
				case AND:
					bson = Filters.and(this.condition);
					break;
				case OR:
					bson = Filters.or(this.condition);
					break;
				case NOT:
					bson = Filters.not(Filters.and(this.condition));
					break;
				case NOR:
					bson = Filters.nor(this.condition);
					break;
				}
			}
			return bson;
		} else {
			return new BsonDocument();
		}
	}

	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key1", "1");
		map.put("key2", "2");
		Expression exp = new Expression(LogicRelate.OR);
		Bson b = exp.toBson();
		System.out.println(b.toString());
	}

}
