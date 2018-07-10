# spring-boot-starter-flowlimitation
接口限流

## 前言
    服务在大流量下很容易被击垮，现在微服务有限流、熔断模块可以保证服务在大流量下稳定工作；而传统的服务该如何限流呢？
    这里抛开NGINX不说，仅从代码层面去实现。
    
## 思路
    通过filter做限流，因为所有的请求都会经过filter，那么可以预先定义一个服务能承受的最大请求数，  
    在filter中对请求计数，  
    过来一个请求计数器加1，  
    处理完一个请求计数器减1，  
    当累计请求数大于或等于设定的最大请求数时，把请求直接返回（可以自定义友好的消息）。  
    像这种工作最好做成统一处理的，你不能让每个开发都去关注这件事情。  
    为此，我基于Spring Boot封装了一个Starter, 内置了AES加密算法。GitHub地址如下：   
     https://github.com/EagleJin/spring-boot-starter-flowlimitation.git
     
## 使用
    先来看看怎么使用，可以下载源码，然后引入即可，然后在启动类上增加@EnableLimit注解开启限流操作：
   ```
    @EnableLimit
    @SpringBootApplication
    public class DemoApplication {
	  public static void main(String[] args) {
		  SpringApplication.run(AppserviceApplication.class, args);
	  }
    }
   ```
    增加配置：
   ```
    # 需要过滤的URL，多个用逗号隔开
    system.limit.url=/test,/limit
    # 是否是过滤生效。默认false生效；true 不生效
    system.limit.debug=false
    # 设置允许的最大请求量
    system.limit.maxRequest=10
    # 限流时被抛弃的请求返回的消息
    system.limit.message="{code:000,message:\"系统忙，请稍后重试...\"}"
   ```
## 原理
   启动类上的@EnableLimit注解是用来开启功能的,通过@Import导入自动配置类
   ```
   @Target({ElementType.TYPE})
   @Retention(RetentionPolicy.RUNTIME)
   @Documented
   @Inherited
   @Import({LimitAutoConfiguration.class})
   public @interface EnableLimit {
   }
   ```
   LimitAutoConfiguration中装载自定义的Filter。
   ```
   @Configuration
   @Component
   @EnableAutoConfiguration
   @EnableConfigurationProperties(LimitProperties.class)
   public class LimitAutoConfiguration {

      @Autowired
      private LimitProperties limitProperties;

      @Bean
      public FilterRegistrationBean filterRegistration() {
         FilterRegistrationBean registrationBean = new FilterRegistrationBean();
         registrationBean.setFilter(limitFilter());
         String url = limitProperties.getUrl();
         if (!StringUtils.isEmpty(url)) {
            String[] param = url.split(",");
            registrationBean.addUrlPatterns(param);
         }
          registrationBean.setName("limitFilter");
          registrationBean.setOrder(1);
          return registrationBean;
        }
        @Bean
        public Filter limitFilter() {
            return new LimitFilter();
        }
   }
   ```
