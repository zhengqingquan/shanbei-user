// package com.github.shanbei.shanbeiuser.filter;
//
// import com.github.shanbei.shanbeiuser.model.domain.redis.RedisCache;
// import com.github.shanbei.shanbeiuser.utils.JwtUtil;
// import io.jsonwebtoken.Claims;
// import org.springframework.stereotype.Component;
// import org.springframework.util.StringUtils;
// import org.springframework.web.filter.OncePerRequestFilter;
// import springfox.documentation.spi.service.contexts.SecurityContext;
//
// import javax.annotation.Resource;
// import javax.servlet.FilterChain;
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.IOException;
// import java.util.Objects;
//
// /**
//  * 继承Spring提供的过滤器的实现类。用Filter接口可能会因为版本问题导致一个请求被调用多次。
//  */
// @Component
// public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
//
//     @Resource
//     private RedisCache redisCache;
//
//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//         // 获取Token
//         String token = request.getHeader("token");
//         if (!StringUtils.hasText(token)){
//             // 放行，这里只是解析，并不做鉴权。后面会有其它的过滤器进行鉴权。
//             filterChain.doFilter(request,response);
//             return;
//         }
//         // 解析Token
//         try {
//             Claims claims = JwtUtil.parseJWT(token);
//             String userid = claims.getSubject();
//         } catch (Exception e) {
//             e.printStackTrace();
//             throw new RuntimeException("token非法");
//         }
//
//         // 从redis中获取用户信息
//         String redisKey = "login:"+userid;
//         Object loginUser = redisCache.getCacheObject(redisKey);
//         if (Objects.isNull(loginUser)){
//             throw new RuntimeException("用户未登录");
//         }
//         // 存入SecurityContextHolder
//         UsernamePas
//         SecurityContextHolder.
//     }
// }
