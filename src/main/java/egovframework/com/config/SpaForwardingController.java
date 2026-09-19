package egovframework.com.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * SPA (React Router) forwarding controller for integrated deployment.
 *
 * When the React build is served from src/main/resources/static/, client-side
 * routes (e.g. /about, /support/qna) have no server mapping and a hard refresh
 * would 404. This controller forwards any non-API, non-asset, extension-less
 * path to index.html so React Router can render it.
 *
 * IMPORTANT — this must NOT swallow REST API paths. The regex below excludes the
 * known API roots and anything containing a dot (static assets like .js/.css/.png).
 * If a new top-level API root is added, add it to the negative lookahead.
 *
 * API roots (as of 2026-09): auth, bbsMaster, bbsUseInf, board, boardFileAtch,
 * boardReply, etc, file, image, jwtAuthAPI, login, mainPage, members, mypage,
 * notUsedBbsMaster, schedule, v3 (springdoc), swagger-ui.
 */
@Controller
public class SpaForwardingController {

    // Match single-segment or multi-segment paths that are NOT an API root and
    // contain no "." (so real files/assets are served normally by the resource handler).
    @RequestMapping(value = {
        "/{path:^(?!auth|bbsMaster|bbsUseInf|board|boardFileAtch|boardReply|etc|file|image|jwtAuthAPI|login|mainPage|members|mypage|notUsedBbsMaster|schedule|v3|swagger-ui|actuator)[^\\.]*$}",
        "/{path:^(?!auth|bbsMaster|bbsUseInf|board|boardFileAtch|boardReply|etc|file|image|jwtAuthAPI|login|mainPage|members|mypage|notUsedBbsMaster|schedule|v3|swagger-ui|actuator)[^\\.]*}/**"
    })
    public String forwardSpaRoutes() {
        return "forward:/index.html";
    }
}
