export ARG="-Dlog4j2.debug -DXlog4j.configurationFile=log4j2-spring.xml"

mvn $ARG -Dspring-boot.run.jvmArguments="$ARG" spring-boot:run
