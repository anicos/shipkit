package org.shipkit.internal.gradle

import org.shipkit.gradle.ReleaseConfiguration
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll
import testutil.ReflectionUtil

import java.lang.reflect.Method

class ReleaseConfigurationGettersAndSettersTest extends Specification {

    @Shared
    def conf = new ReleaseConfiguration()

    def "default values"() {
        conf.team.developers.empty
        conf.team.contributors.empty
        conf.git.commitMessagePostfix == "[ci skip]"
        conf.releaseNotes.ignoreCommitsContaining == ["[ci skip]"]
    }

    @Unroll
    def "maximum of two numbers"(row,Method setter,Method getter,c) {
        def randomValue = getRandomValueForSetter(setter)

        setter.invoke(c,randomValue)

        expect:


        getter.invoke(c) == randomValue

        where:
        row << ReflectionUtil.findGettersAndSetters(conf)
        setter = row.setter
        getter = row.getter
        c = row.object

    }

    def getRandomValueForSetter(Method setter){
        if (setter.parameters[0].type == String.class){
            return "a"
        }

        if (setter.parameters[0].type.name == "boolean") {
            return true;
        }

        if (setter.parameters[0].type == Map.class){
            def emptyMap = [:]
            emptyMap.put("a","a")
            return emptyMap
        }

        if (setter.parameters[0].type == Collection.class){
            def emptyMap = []
            emptyMap<<"a"
            return emptyMap
        }

        return "a"
    }
}
