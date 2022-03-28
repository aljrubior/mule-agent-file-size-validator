package com.mycompany.agent.exceptions;

import com.mulesoft.agent.exception.ArtifactValidationException;

public class FileSizeLimitExceededException extends ArtifactValidationException {

    /**
     * <p>
     * Creates an instance of the exception with a message explaining the cause.
     * </p>
     * @param message The message explaining the cause of the exception.
     */
    public FileSizeLimitExceededException(String message)
    {
        super(message);
    }

    /**
     * <p>
     * Creates an instance of the exception with a message explaining the cause and the exception that triggered it.
     * </p>
     * @param message The message explaining the cause of the exception.
     * @param originalCause The exception that triggered this exception.
     */
    public FileSizeLimitExceededException(String message, Exception originalCause)
    {
        super(message, originalCause);
    }

}

