package club.zhouyouwu.graduate.gateway.config;

//@Component
//public class UnionResultGatewayFilterFactory extends ModifyResponseBodyGatewayFilterFactory {
//    @Override
//    public GatewayFilter apply(Config config) {
//        return new ModifyResponseGatewayFilter(this.getConfig());
//    }
//
//
//    private Config getConfig() {
//        Config cf = new Config();
//        // Config.setRewriteFunction(Class<T> inClass, Class<R> outClass, RewriteFunction<T, R> rewriteFunction)
//        // inClass 原数据类型，可以指定为具体数据类型，我这里指定为Object,是为了处理多种数据类型。
//        //                      当然支持多接口返回多数据类型的统一修改，yaml中的配置，path,uri需要做相关调整
//        // outClass 目标数据类型
//        // rewriteFunction 内容重写方法
//        cf.setRewriteFunction(Result.class, Result.class, getRewriteFunction());
//        return cf;
//    }
//
//    private RewriteFunction<Result, Result> getRewriteFunction() {
//
//        return (exchange, resp) -> Mono.just(Result::setResult(exchange.getRequest().getHeaders().getFirst("cn-buddie.demo.requestId")).result(resp).build());
//    }
//}
