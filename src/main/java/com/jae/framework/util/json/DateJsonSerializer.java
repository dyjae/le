package com.jae.framework.util.json;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * jackson转换JSON时格式化日期的标注	yyyy-MM-dd
 * @author Jae
 *
 */
public class DateJsonSerializer extends JsonSerializer<Date> {
	private DateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd ");

	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		gen.writeString(dateFormat.format(date));
	}
}
