package com.mycompany.agent;

import com.mulesoft.agent.exception.ArtifactValidationException;
import com.mulesoft.agent.services.ArtifactValidator;
import com.mycompany.agent.exceptions.FileSizeLimitExceededException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Named;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import static java.lang.String.format;

@Named("FileSizeArtifactValidator")
@Singleton
public class MuleAgentFileSizeValidator implements ArtifactValidator {

    private static final Logger LOGGER = LogManager.getLogger(MuleAgentFileSizeValidator.class);

    public static final String APPLICATION_NAME_KEY = "_APPLICATION_NAME";
    public static final String APPLICATION_FILE_PATH_KEY = "_APPLICATION_FILE_PATH";
    public static final String LIMIT_ARGS_KEY = "limit";

    public String getType() {
        return "fileSizeValidator";
    }

    public String getName() {
        return "defaultFileSize";
    }

    public void validate(Map<String, String> args) throws ArtifactValidationException {

        String applicationFilePath = args.get(APPLICATION_FILE_PATH_KEY);
        String applicationName = args.get(APPLICATION_NAME_KEY);
        String limitArg = args.get(LIMIT_ARGS_KEY);
        int limit =  Integer.parseInt(limitArg);

        File file = new File(applicationFilePath);

        long fileSizeInBytes = file.length();
        long fileSizeInKB = fileSizeInBytes / 1024;
        long fileSizeInMB = fileSizeInKB / 1024;

        System.out.println(format("File path: %s", applicationFilePath));
        System.out.println(format("File size (MB): %s", fileSizeInMB));

        if (fileSizeInMB > limit) {
            String message = format("File size exceeds the maximum limit '%s MB': Application: '%s' Size:'%s MB'.", limit, applicationName, fileSizeInMB);
            throw new FileSizeLimitExceededException(message);
        }
    }
}
