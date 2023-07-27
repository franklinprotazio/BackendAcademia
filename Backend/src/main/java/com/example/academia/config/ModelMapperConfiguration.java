package com.example.academia.config;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.academia.core.entity.Academia;
import com.example.academia.v1.dto.AcademiaDTO;

@Configuration
public class ModelMapperConfiguration {

	@Bean
	public static ModelMapper getModelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		modelMapper.addConverter(converterStringToString());
		modelMapper.typeMap(Academia.class, AcademiaDTO.class);

		return modelMapper;
	}

	private static Converter<String, String> converterStringToString() {
		return new AbstractConverter<String, String>() {
			protected String convert(String source) {
				return source == null ? null : source.trim();
			}
		};
	}
	
	
}
