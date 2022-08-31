package org.sample;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)

@State(Scope.Benchmark)
public class BenchmarkMethod2 {

    public Coursework myCoursework;

    @Setup(Level.Iteration)
    public void setUp(){
        this.myCoursework = new Coursework("/Users/mohammedhussain/Desktop/data.csv");
    }

    @Param({"Didcot","Deal","Havant"})
    public String c;
    @Benchmark
    public void method2Fast(Blackhole blackhole) {
        blackhole.consume(myCoursework.getCountOfCustomersWhoLiveIn(c));
    }

    @Benchmark
    public void method2Slow(Blackhole blackhole) {
        blackhole.consume(myCoursework.getCountOfCustomersWhoLiveInSlow(c));
    }

}
