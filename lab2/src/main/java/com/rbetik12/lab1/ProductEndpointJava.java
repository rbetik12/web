//package com.rbetik12.lab1;
//
//import com.rbetik12.generated.CreateProductRequest;
//import com.rbetik12.generated.DeleteProductRequest;
//import com.rbetik12.generated.GetProductResponse;
//import com.rbetik12.generated.ProductXSD;
//import com.rbetik12.generated.ReadProductRequest;
//import com.rbetik12.generated.StatusResponse;
//import com.rbetik12.generated.UpdateProductRequest;
//import com.rbetik12.lab1.Product;
//import com.rbetik12.lab1.ProductRepo;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Optional;
//import kotlin.Metadata;
//import kotlin.jvm.internal.DefaultConstructorMarker;
//import kotlin.jvm.internal.Intrinsics;
//import kotlin.jvm.internal.SourceDebugExtension;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ws.server.endpoint.annotation.Endpoint;
//import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
//import org.springframework.ws.server.endpoint.annotation.RequestPayload;
//import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
//
//@Endpoint
//@Metadata(
//        mv = {1, 8, 0},
//        k = 1,
//        d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0017\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\bH\u0017J\u0012\u0010\t\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\nH\u0017J\u0012\u0010\u000b\u001a\u00020\f2\b\b\u0001\u0010\u0007\u001a\u00020\rH\u0017J\u0012\u0010\u000e\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\u000fH\u0017R\u000e\u0010\u0002\u001a\u00020\u0003X\u0092\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"},
//        d2 = {"Lcom/rbetik12/lab1/soap/ProductEndpoint;", "", "productRepo", "Lcom/rbetik12/lab1/ProductRepo;", "(Lcom/rbetik12/lab1/ProductRepo;)V", "create", "Lcom/rbetik12/generated/StatusResponse;", "request", "Lcom/rbetik12/generated/CreateProductRequest;", "delete", "Lcom/rbetik12/generated/DeleteProductRequest;", "read", "Lcom/rbetik12/generated/GetProductResponse;", "Lcom/rbetik12/generated/ReadProductRequest;", "update", "Lcom/rbetik12/generated/UpdateProductRequest;", "Companion", "lab1"}
//)
//@SourceDebugExtension({"SMAP\nProductEndpoint.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ProductEndpoint.kt\ncom/rbetik12/lab1/soap/ProductEndpoint\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,105:1\n1855#2,2:106\n*S KotlinDebug\n*F\n+ 1 ProductEndpoint.kt\ncom/rbetik12/lab1/soap/ProductEndpoint\n*L\n31#1:106,2\n*E\n"})
//public class ProductEndpointJava {
//
//    @Autowired
//    private final ProductRepo productRepo = null;
//    @NotNull
//    public static final String NAMESPACE_URI = "http://localhost:8081/products/soap";
//    @NotNull
//    public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
//
//    @PayloadRoot(
//            namespace = "http://localhost:8081/products/soap",
//            localPart = "read"
//    )
//    @ResponsePayload
//    @NotNull
//    public GetProductResponse read(@RequestPayload @NotNull ReadProductRequest request) {
//        Intrinsics.checkNotNullParameter(request, "request");
//        ProductRepo var10000 = this.productRepo;
//        Long var10001 = request.getId();
//        Intrinsics.checkNotNullExpressionValue(var10001, "request.id");
//        long var12 = var10001;
//        String var10002 = request.getName();
//        Intrinsics.checkNotNullExpressionValue(var10002, "request.name");
//        Float var10003 = request.getPrice();
//        Intrinsics.checkNotNullExpressionValue(var10003, "request.price");
//        float var13 = var10003;
//        Long var10004 = request.getSellAmount();
//        Intrinsics.checkNotNullExpressionValue(var10004, "request.sellAmount");
//        long var14 = var10004;
//        String var10005 = request.getProducedBy();
//        Intrinsics.checkNotNullExpressionValue(var10005, "request.producedBy");
//        List res = var10000.findProductsByParams(var12, var10002, var13, var14, var10005);
//        ArrayList productList = new ArrayList();
//        Iterable $this$forEach$iv = (Iterable)res;
//        boolean $i$f$forEach = false;
//        Iterator var6 = $this$forEach$iv.iterator();
//
//        while(var6.hasNext()) {
//            Object element$iv = var6.next();
//            Product it = (Product)element$iv;
//            boolean var9 = false;
//            ProductXSD p = new ProductXSD();
//            p.setId(it.getId());
//            p.setName(it.getName());
//            p.setPrice(it.getPrice());
//            p.setProducedBy(it.getProducedBy());
//            p.setSellAmount(it.getSellAmount());
//            productList.add(p);
//        }
//
//        GetProductResponse response = new GetProductResponse();
//        response.getProducts().addAll((Collection)productList);
//        return response;
//    }
//
//    @PayloadRoot(
//            namespace = "http://localhost:8081/products/soap",
//            localPart = "create"
//    )
//    @ResponsePayload
//    @NotNull
//    public StatusResponse create(@RequestPayload @NotNull CreateProductRequest request) {
//        Intrinsics.checkNotNullParameter(request, "request");
//        String var10003 = request.getName();
//        Intrinsics.checkNotNullExpressionValue(var10003, "request.name");
//        String var10004 = request.getProducedBy();
//        Intrinsics.checkNotNullExpressionValue(var10004, "request.producedBy");
//        long var10005 = request.getSellAmount();
//        float var3 = request.getPrice();
//        long var4 = var10005;
//        String var6 = var10004;
//        Product newProduct = new Product(0L, var10003, var3, var4, var6);
//        this.productRepo.save(newProduct);
//        StatusResponse response = new StatusResponse();
//        response.setCode(200L);
//        return response;
//    }
//
//    @PayloadRoot(
//            namespace = "http://localhost:8081/products/soap",
//            localPart = "delete"
//    )
//    @ResponsePayload
//    @NotNull
//    public StatusResponse delete(@RequestPayload @NotNull DeleteProductRequest request) {
//        Intrinsics.checkNotNullParameter(request, "request");
//        Long id = request.getId();
//        this.productRepo.deleteById(id);
//        StatusResponse response = new StatusResponse();
//        response.setCode(200L);
//        return response;
//    }
//
//    @PayloadRoot(
//            namespace = "http://localhost:8081/products/soap",
//            localPart = "update"
//    )
//    @ResponsePayload
//    @NotNull
//    public StatusResponse update(@RequestPayload @NotNull UpdateProductRequest request) {
//        Intrinsics.checkNotNullParameter(request, "request");
//        Optional productOpt = this.productRepo.findById(request.getId());
//        Intrinsics.checkNotNullExpressionValue(productOpt, "productOpt");
//        if (productOpt.isEmpty()) {
//            StatusResponse response = new StatusResponse();
//            response.setCode(404L);
//            response.setErrorMessage("Can't find entity with id " + request.getId() + " for update");
//            return response;
//        } else {
//            Object var10000 = productOpt.get();
//            Intrinsics.checkNotNullExpressionValue(var10000, "productOpt.get()");
//            Product product = (Product)var10000;
//            String var10001 = request.getName();
//            Intrinsics.checkNotNullExpressionValue(var10001, "request.name");
//            product.setName(var10001);
//            product.setPrice(request.getPrice());
//            var10001 = request.getProducedBy();
//            Intrinsics.checkNotNullExpressionValue(var10001, "request.producedBy");
//            product.setProducedBy(var10001);
//            product.setSellAmount(request.getSellAmount());
//            this.productRepo.save(product);
//            StatusResponse response = new StatusResponse();
//            response.setCode(200L);
//            return response;
//        }
//    }
//
//    @Metadata(
//            mv = {1, 8, 0},
//            k = 1,
//            d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0005"},
//            d2 = {"Lcom/rbetik12/lab1/soap/ProductEndpoint$Companion;", "", "()V", "NAMESPACE_URI", "", "lab1"}
//    )
//    public static final class Companion {
//        private Companion() {
//        }
//
//        // $FF: synthetic method
//        public Companion(DefaultConstructorMarker $constructor_marker) {
//            this();
//        }
//    }
//}
