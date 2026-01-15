package  com.ledger.mcp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.ledger.mcp.service.TransactionService;

@Slf4j
@SpringBootApplication
public class LedgerMcpApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(LedgerMcpApplication.class, args);
//        app.setBannerMode(Banner.Mode.OFF);
//        app.setWebApplicationType(WebApplicationType.NONE);
//        app.setRegisterShutdownHook(false); // <- prevents premature exit
//        app.run(args);
//
//        // send MCP ready message (valid JSON-RPC)
//        System.out.println("{\"jsonrpc\":\"2.0\",\"id\":0,\"result\":{}}");
//        System.out.flush();
//
//        // redirect logs to stderr
//        System.err.println("Ledger MCP Server started, awaiting commands...");
//
//        // keep JVM alive indefinitely
//        Thread.currentThread().join();
    }

    @Bean
    public ToolCallbackProvider transactionMCPTool(TransactionService transService) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(transService)
                .build();
    }
}
