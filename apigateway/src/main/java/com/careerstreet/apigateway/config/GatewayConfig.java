package com.careerstreet.apigateway.config;
import com.careerstreet.apigateway.Constants;
import com.careerstreet.apigateway.filter.JwtRoleAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.server.ServerWebExchange;



import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Configuration
@RequiredArgsConstructor
public class GatewayConfig {
    private final JwtRoleAuthenticationFilter jwtRoleAuthenticationFilter;

    private GatewayFilter applyJwtAuthentication(List<String> allowedRoles) {
        return (exchange, chain) -> jwtRoleAuthenticationFilter.filter(exchange, chain)
                .doFinally(signalType -> processRoles(exchange, chain, allowedRoles));
    }


    private void processRoles(ServerWebExchange exchange, GatewayFilterChain chain, List<String> allowedRoles) {
        List<String> roles = exchange.getAttribute("roles");
        if (roles != null && !Collections.disjoint(roles, allowedRoles)) {
            chain.filter(exchange); // Continue with the chain if roles contain any of the allowedRoles
        } else {
            jwtRoleAuthenticationFilter.onError(exchange); // Handle unauthorized access
        }
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder){
        List<String> allowedRoles = Arrays.asList("admin", "employer","candidate"); // Example of allowed roles

        return builder.routes()
                // User route
                .route("user-service", r->r.path(
                                Constants.USER_PREFIX+"/register"
                                ,Constants.USER_PREFIX+"/login"
                                ,Constants.USER_PREFIX+ "/**"
                                ,Constants.USER_PREFIX+ "/get-email/{username}"

                        ).and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
                        //.filters(f -> f.filter(applyJwtAuthentication(allowedRoles)))
                        .uri("lb://user-service"))

                // Candidate + CV route
                .route("candidate-service", r->r.path(
                                Constants.CANDIDATE_PREFIX+"/create"
                                ,Constants.CANDIDATE_PREFIX+ "/getbyusername/{username}"
                                ,Constants.CANDIDATE_PREFIX+ "/getidbyusername/{username}"
                                ,Constants.CANDIDATE_PREFIX+ "/update/{id}"
                                ,Constants.CANDIDATE_PREFIX+ "/list/all"
                        // CV
                                ,Constants.CANDIDATECV_PREFIX + "/create"
                                ,Constants.CANDIDATECV_PREFIX + "/update/{id}"
                                ,Constants.CANDIDATECV_PREFIX + "/delete/{id}"
                                ,Constants.CANDIDATECV_PREFIX + "/get/{id}"
                                ,Constants.CANDIDATECV_PREFIX + "/by-candidate/{candidateId}"
                        ).and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
                        //.filters(f -> f.filter(applyJwtAuthentication(allowedRoles)))
                        .uri("lb://candidate-service"))

                // Employer route
                .route("employer-service", r->r.path(
                        Constants.EMPLOYER_PREFIX+"/create"
                        ,Constants.EMPLOYER_PREFIX+ "/getbyusername/{username}"
                        ,Constants.EMPLOYER_PREFIX+ "/getidbyusername/{username}"
                        ,Constants.EMPLOYER_PREFIX + "/update/{id}"
                        ,Constants.EMPLOYER_PREFIX + "/list/all"
                        ,Constants.EMPLOYER_PREFIX + "/getemployer/{employerId}"
                ).and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
                        //.filters(f -> f.filter(applyJwtAuthentication(allowedRoles)))
                        .uri("lb://employer-service"))

                // Admin+Blog route
                .route("admin-service", r->r.path(
                                Constants.ADMIN_PREFIX+"/create"
                                    ,Constants.ADMIN_PREFIX+"/getbyusername/{username}"
                        // BLOG
                                ,Constants.BLOG_PREFIX+ "/create"
                        ).and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
                        //.filters(f -> f.filter(applyJwtAuthentication(allowedRoles)))
                        .uri("lb://admin-service"))

                // Job+Level route
                .route("job-service", r->r.path(
                                Constants.JOB_PREFIX+"/create"
                                ,Constants.JOB_PREFIX+ "/update/{jobId}"
                                ,Constants.JOB_PREFIX+ "/get/{id}"
                                ,Constants.JOB_PREFIX+ "/getJobByEmployer/{employerId}"
                                ,Constants.JOB_PREFIX+ "/getall"
                                ,Constants.JOB_PREFIX+ "/getall/status/{status}"
                                ,Constants.JOB_PREFIX+ "/get-name/{id}"
                                ,Constants.JOB_PREFIX+ "/update/{jobId}/jobstatus/{status}"
                                ,Constants.JOB_PREFIX+ "/fillter"
                        // LEVEL
                                ,Constants.LEVEL_PREFIX+ "/create"
                                ,Constants.LEVEL_PREFIX+ "/getall"
                                ,Constants.LEVEL_PREFIX+ "/get-level/{levelId}"
                        ).and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
                        //.filters(f -> f.filter(applyJwtAuthentication(allowedRoles)))
                        .uri("lb://job-service"))

                // App route
                .route("apply-service", r->r.path(
                                Constants.APPLY_PREFIX+"/create"
                                ,Constants.APPLY_PREFIX+ "/update/{id}/applystatus/{status}"
                                ,Constants.APPLY_PREFIX+ "/getJobByStatus/{status}"
                                ,Constants.APPLY_PREFIX+ "/getAppliesByCandidateId/{candidateId}"
                                ,Constants.APPLY_PREFIX+ "/getAppliesByJobId/{jobId}"
                                ,Constants.APPLY_PREFIX+ "/check-application"
                                ,Constants.APPLY_PREFIX+ "/getAppliesByEmployerId/{employerId}"
                                ,Constants.APPLY_PREFIX+ "/getAppliesByCandidateCv/{candidateCvId}"
                        ).and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
                        //.filters(f -> f.filter(applyJwtAuthentication(allowedRoles)))
                        .uri("lb://apply-service"))

                // Tech route
                .route("tech-service", r->r.path(
                                Constants.TECH_PREFIX+"/create"
                                ,Constants.TECH_PREFIX+"/getall"
                        // Tech-Detail
                                ,Constants.TECHDETAIL_PREFIX+"/create"
                                ,Constants.TECHDETAIL_PREFIX+"/get-tech/{jobId}"
                        ).and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
                        //.filters(f -> f.filter(applyJwtAuthentication(allowedRoles)))
                        .uri("lb://tech-service"))
                // File route
                .route("file-service", r->r.path(
                                Constants.FILE_PREFIX+"/upload"
                        ).and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
                        //.filters(f -> f.filter(applyJwtAuthentication(allowedRoles)))
                        .uri("lb://file-service"))
                // Notification route
                .route("notification-service", r->r.path(
                                Constants.NOTIFICATION_PREFIX+"/create"
                        ).and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
                        //.filters(f -> f.filter(applyJwtAuthentication(allowedRoles)))
                        .uri("lb://notification-service"))

                // Save route
                .route("save-service", r->r.path(
                                Constants.SAVE_PREFIX+"/create"
                        ,Constants.SAVE_PREFIX+"/{candidateId}"
                        ).and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT)
                        //.filters(f -> f.filter(applyJwtAuthentication(allowedRoles)))
                        .uri("lb://save-service"))
                .build();
    }
}
