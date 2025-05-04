package org.kmp.shots.hugging.face.llm.sample

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.lang.ProcessBuilder

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {

    routing {
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }


        /**
         *  If you use the script way other ways you can use ktor client
         *  to make a request to fast api
         */
        post("/summarize") {
            val textToSummarize = call.parameters["text"] ?: DefualtText
            val process = ProcessBuilder("../.venv/bin/python3", "../mlgate/main.py", textToSummarize)
                .redirectErrorStream(true)
                .start()

            val result = process.inputStream.bufferedReader().readText()

            call.respondText("Summary: $result")
        }
    }
}

private const val DefualtText =
    "The phenomenon known as the \"urban heat island\" effect describes how metropolitan areas tend to experience significantly warmer temperatures than their surrounding rural areas. This temperature difference is primarily caused by human activities and 1  the modification of land surfaces. Buildings, roads, and other infrastructure absorb and retain more solar radiation than natural landscapes like forests and bodies of water. Additionally, waste heat generated from vehicles, factories, and air conditioning systems contributes to the warming. The lack of vegetation in cities also reduces the cooling effect provided by evapotranspiration. Urban heat islands can impact communities by increasing energy demand (especially for cooling), elevating air pollution levels, compromising human health (particularly during heatwaves), and affecting water quality. Mitigation strategies often involve increasing green spaces, using reflective or green roofs, and promoting energy-efficient urban design."