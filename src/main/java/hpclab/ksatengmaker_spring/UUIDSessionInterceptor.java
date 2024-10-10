package hpclab.ksatengmaker_spring;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class UUIDSessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session = request.getSession();

        // 세션에 UUID가 없으면 생성
        if (session.getAttribute("userUUID") == null) {
            UUID uuid = UUID.randomUUID();
            session.setAttribute("userUUID", uuid.toString());
            System.out.println("New UUID generated and stored in session: " + uuid);
        }

        // 계속 진행
        return true;
    }
}
