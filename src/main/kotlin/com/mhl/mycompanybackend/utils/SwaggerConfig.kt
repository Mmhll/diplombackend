package com.mhl.mycompanybackend.utils

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.ParameterBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.schema.ModelRef
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ApiKey
import springfox.documentation.service.Parameter
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
open class SwaggerConfig {
    @Bean
    open fun api(): Docket {
        val parameterBuilder = ParameterBuilder()
        parameterBuilder.name("Authorization")
                .modelRef(ModelRef("string"))
                .parameterType("header")
                .description("JWT token")
                .required(true)
                .build()
        val parameters: MutableList<Parameter> = ArrayList()
        parameters.add(parameterBuilder.build())
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .apis(RequestHandlerSelectors.basePackage("com.mhl"))
                .build()
                .globalOperationParameters(parameters)
                .apiInfo(apiInfo())
                .securitySchemes(listOf(apiKey()))
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title("Sig-Predict REST API Document")
                .description("work in progress")
                .termsOfServiceUrl("localhost")
                .version("1.0")
                .build()
    }

    private fun apiKey(): ApiKey {
        return ApiKey("jwtToken", "Authorization", "header")
    }
}