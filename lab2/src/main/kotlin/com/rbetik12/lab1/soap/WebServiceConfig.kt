package com.rbetik12.lab1.soap

import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.ws.config.annotation.EnableWs
import org.springframework.ws.config.annotation.WsConfigurerAdapter
import org.springframework.ws.transport.http.MessageDispatcherServlet
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition
import org.springframework.xml.xsd.SimpleXsdSchema
import org.springframework.xml.xsd.XsdSchema


@EnableWs
@Configuration
class WebServiceConfig : WsConfigurerAdapter() {
    @Bean
    fun messageDispatcherServlet(applicationContext: ApplicationContext?): ServletRegistrationBean<MessageDispatcherServlet> {
        val servlet = MessageDispatcherServlet()
        servlet.setApplicationContext(applicationContext)
        servlet.isTransformWsdlLocations = true
        return ServletRegistrationBean(servlet, "/products/soap/*")
    }

    @Bean(name = ["products"])
    fun defaultWsdl11Definition(countriesSchema: XsdSchema?): DefaultWsdl11Definition {
        val wsdl11Definition = DefaultWsdl11Definition()
        wsdl11Definition.setPortTypeName("ProductsPort")
        wsdl11Definition.setLocationUri("/products/soap/")
        wsdl11Definition.setTargetNamespace(ProductEndpoint.NAMESPACE_URI)
        wsdl11Definition.setSchema(countriesSchema)
        return wsdl11Definition
    }

    @Bean
    fun countriesSchema(): XsdSchema {
        return SimpleXsdSchema(ClassPathResource("products.xsd"))
    }
}
