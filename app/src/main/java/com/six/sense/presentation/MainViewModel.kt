package com.six.sense.presentation

import androidx.lifecycle.SavedStateHandle
import com.openai.client.OpenAIClient
import com.openai.models.*
import com.six.sense.presentation.base.BaseViewModel
import com.six.sense.utils.log
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val openAIClient: OpenAIClient
) : BaseViewModel() {

    fun testOpenAI(){
        launch(ioContext){
            val assistantId= openAIClient.beta().assistants().create(
                BetaAssistantCreateParams.builder()
                    .model(ChatModel.GPT_4O)
                    .instructions("")
                    .tools(listOf(AssistantTool.ofFunctionTool(FunctionTool.builder().type(FunctionTool.Type.FUNCTION).build())))
                    .build()
            ).id()
            openAIClient.beta().threads().runs().create(
                BetaThreadRunCreateParams.builder()
                    .assistantId(assistantId)
                    .instructions("")
                    .build()
            )

            openAIClient.models().list(
                ModelListParams.builder()
                    .build()
            ).data().map { it.id() }.log()
            val params = ChatCompletionCreateParams.builder()
                .messages(listOf(
                    ChatCompletionMessageParam.ofChatCompletionUserMessageParam(
                        ChatCompletionUserMessageParam.builder()
                        .role(ChatCompletionUserMessageParam.Role.USER)
                        .content(ChatCompletionUserMessageParam.Content.ofTextContent("Hay, this is a test"))
                        .build())))
                .model(ChatModel.GPT_3_5_TURBO)
                .build()
            val chatCompletion: ChatCompletion = openAIClient.chat().completions().create(params)
            chatCompletion.choices().first().message().log()
        }

    }

}