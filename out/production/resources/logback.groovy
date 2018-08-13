import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.filter.ThresholdFilter
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy

import static ch.qos.logback.classic.Level.*

def APP_LOG_PATH = System.getProperty("APP_LOG_PATH")
def WAS_ENV = System.getProperty("WAS_ENV")

scan("30 seconds")

appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d - %X{CorrelationId}: %m%n"
    }
    filter(ThresholdFilter) {
        level = TRACE
    }
}

appender("LOGFILE", RollingFileAppender) {
    file = "${APP_LOG_PATH}/gld_MyRestTest.log"
    rollingPolicy(FixedWindowRollingPolicy) {
        fileNamePattern = "gld_MyRestTest.%i.log"
        maxIndex = 10
    }
    triggeringPolicy(SizeBasedTriggeringPolicy) {
        maxFileSize = "5MB"
    }
    encoder(PatternLayoutEncoder) {
        pattern = "%d [%t] %-5p %c - %X{CorrelationId}: %m%n"
    }
    filter(ThresholdFilter) {
        level = TRACE
    }
}

logger("net.bull.javamelody", ERROR, ["LOGFILE"], false)
logger("com.mutualofomaha.config.javamelody", DEBUG)
logger("com.mangofactory.swagger", DEBUG)
logger("org.hibernate", OFF)
//logger("org.springframework.beans", DEBUG)
logger('com.mutualofomaha.gld.myresttest', DEBUG)
logger("org.apache", ERROR)
logger("springfox.documentation", OFF)
root(INFO, ["CONSOLE", "LOGFILE"])
