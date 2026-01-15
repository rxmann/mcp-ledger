package  com.ledger.mcp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.ledger.mcp.service.TransactionService;

@Slf4j
@SpringBootApplication
public class LedgerMcpApplication {

    public static void main(String[] args) {
        // Set properties before creating the application
        System.setProperty("spring.main.web-application-type", "none");

        SpringApplication app = new SpringApplication(LedgerMcpApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.setLogStartupInfo(false);
        app.run(args);
    }

    @Bean
    public ToolCallbackProvider transactionMCPTool(TransactionService transService) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(transService)
                .build();
    }

    @Bean
    public CommandLineRunner startupInfo() {
        return args -> {
            log.info("\n{}", "=".repeat(60));
            log.info("Ledger MCP Server is starting...");
            log.info("{}\n", "=".repeat(60));
        };
    }
}