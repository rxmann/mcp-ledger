package  com.ledger.mcp;

import com.ledger.mcp.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class LedgerMcpApplication {
    private static final Logger logger = LoggerFactory.getLogger(LedgerMcpApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(LedgerMcpApplication.class, args);
    }

    @Bean
    public ToolCallbackProvider transactionTools(TransactionService transService) {
        return MethodToolCallbackProvider.builder().toolObjects(transService).build();
    }


    @Bean
    public CommandLineRunner startupInfo() {
        return args -> {
            logger.debug("\n{}", "=".repeat(60));
            logger.debug("Ledger MCP Server is starting...");
            logger.debug("{}\n", "=".repeat(60));
        };
    }
}