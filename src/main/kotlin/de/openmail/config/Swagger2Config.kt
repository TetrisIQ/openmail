package de.openmail.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.service.ApiInfo
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@Configuration
@EnableSwagger2
class Swagger2Config {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors
                .basePackage("de.openmail.controller"))
            .paths(PathSelectors.regex("/.*"))
            .build().apiInfo(apiEndPointsInfo())
    }

    private fun apiEndPointsInfo(): ApiInfo {
        return ApiInfoBuilder().title("Openmail")
            .description("is a RESTful Email client ")
            .contact(Contact("TetrisIQ", "https://github.com/TetrisIQ/openmail", "tetrisiq@web.de"))
            .license("GNU General Public License v3.0")
            .licenseUrl("https://github.com/TetrisIQ/openmail/blob/master/LICENSE")
            .version("1.0.0")
            .build()
    }

}
