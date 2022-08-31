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
public class BenchmarkMethod3 {

    public Coursework myCoursework;

    @Setup(Level.Iteration)
    public void setUp(){
        this.myCoursework = new Coursework("/Users/mohammedhussain/Desktop/data.csv");
    }

    @Param({"1","5","10","100","600"})
    public int number;
    @Benchmark
    public void method3Slow(Blackhole blackhole) {
        blackhole.consume(myCoursework.getCustomersForMostRecentOrdersSlow(number));
    }

    @Benchmark
    public void method3Fast(Blackhole blackhole) {
        blackhole.consume(myCoursework.getCustomersForMostRecentOrders(number));
    }

}

