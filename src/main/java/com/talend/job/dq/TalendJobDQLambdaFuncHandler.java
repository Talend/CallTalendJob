package com.talend.job.dq;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.util.Collections;

public class TalendJobDQLambdaFuncHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context) {
        APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent = new APIGatewayProxyResponseEvent();
        LambdaLogger logger = context.getLogger();
        String requestString = apiGatewayProxyRequestEvent.getBody();
        logger.log(requestString);
        logger.log(apiGatewayProxyRequestEvent.toString());
        final local_project.emailvalidation_0_1.emailValidation job = new local_project.emailvalidation_0_1.emailValidation();
        String[] var = new String[] {
                "--context_param input="+requestString
        };
        String[][] bufferOutput = job.runJob(var);
        logger.log(bufferOutput[0][0]);
        generateResponse(apiGatewayProxyResponseEvent, bufferOutput[0][0]);

        return apiGatewayProxyResponseEvent;
    }

    private void generateResponse(APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent, String requestMessage) {
        apiGatewayProxyResponseEvent.setHeaders(Collections.singletonMap("timeStamp", String.valueOf(System.currentTimeMillis())));
        apiGatewayProxyResponseEvent.setStatusCode(200);
        apiGatewayProxyResponseEvent.setBody(requestMessage);
    }
}
