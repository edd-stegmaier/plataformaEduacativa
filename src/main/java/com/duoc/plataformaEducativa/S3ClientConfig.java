package com.duoc.plataformaEducativa;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

@Configuration
public class S3ClientConfig {

	@Bean
	public S3Client s3Client(
			@Value("${aws.region:${AWS_REGION:}}") String configuredRegion,
			@Value("${aws.s3.endpoint:}") String endpoint,
			@Value("${aws.s3.path-style-access:false}") boolean pathStyleAccess) {

		S3ClientBuilder builder = S3Client.builder()
				.serviceConfiguration(S3Configuration.builder()
						.pathStyleAccessEnabled(pathStyleAccess)
						.build());

		if (StringUtils.hasText(configuredRegion)) {
			builder.region(Region.of(configuredRegion));
		}

		if (StringUtils.hasText(endpoint)) {
			builder.endpointOverride(URI.create(endpoint));
		}

		return builder.build();
	}
}