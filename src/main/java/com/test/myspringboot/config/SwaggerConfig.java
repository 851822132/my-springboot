package com.test.myspringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: ysh
 * @date: 2019/3/20 16:04
 * @Description:
 * @Api：表示标识这个类是swagger的资源
 * @ApiOperation：描述针对特定路径的操作或HTTP方法
 * @ApiImplicitParam：表示API操作中的单个参数
 * @ApiImplicitParams：允许多个ApiImplicitParam对象列表的包装器
 * @ApiModel：提供关于Swagger模型的额外信息
 * @ApiModelProperty：添加和操作模型属性的数据
 * @ApiParam：为操作参数添加额外的元数据
 * @ApiResponse：描述一个操作的可能响应
 * @ApiResponses：允许多个ApiResponse对象列表的包装器
 * @ResponseHeader：表示可以作为响应的一部分提供的标头
 * @Authorization：声明要在资源或操作上使用的授权方案
 * @AuthorizationScope：描述OAuth2授权范围
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.test.myspringboot.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("springBoot测试")
                .description("by ysh")
                .termsOfServiceUrl("m.ahqyc.com")
                .version("1.0")
                .build();
    }

}
