package com.epam.port.logic;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Port {

    private static final ReentrantLock INSTANCE_LOCK = new ReentrantLock();
    private static final ReentrantLock PIERS_LOCK = new ReentrantLock();
    private static final ReentrantLock CARGO_LOCK = new ReentrantLock();
    private static final int INITIAL_CARGO = 13000;
    private static Port instance = null;

    private final AtomicInteger cargo = new AtomicInteger(INITIAL_CARGO);
    private final Deque<Pier> piers = new LinkedList<Pier>(
            Arrays.asList(
                    new Pier(this),
                    new Pier(this),
                    new Pier(this)
            )
    );
    private final Semaphore piersSemaphore = new Semaphore(piers.size());

    private Port() {
    }

    public static Port getInstance() {
        if (instance == null) {
            INSTANCE_LOCK.lock();
            try {
                Port local = new Port();
                if (instance == null) {
                    instance = local;
                }
            } finally {
                INSTANCE_LOCK.unlock();
            }
        }
        return instance;
    }

    public Pier acquirePier() throws InterruptedException {
        piersSemaphore.acquire();
        Pier local;
        PIERS_LOCK.lock();
        try {
            local = piers.poll();
        } finally {
            PIERS_LOCK.unlock();
        }
        return local;
    }

    public void releasePier(Pier pier) {
        PIERS_LOCK.lock();
        try {
            piers.offer(pier);
        } finally {
            PIERS_LOCK.unlock();
        }
        piersSemaphore.release();
    }

    public int getCargoValue() {
        return cargo.get();
    }

    public void incrementCargo() {
        cargo.incrementAndGet();
    }

    public boolean decrementCargo() {
        CARGO_LOCK.lock();
        try {
            if (cargo.get() > 0) {
                cargo.decrementAndGet();
                return true;
            }
        } finally {
            CARGO_LOCK.unlock();
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Port port = (Port) o;
        return Objects.equals(cargo, port.cargo) &&
                Objects.equals(piers, port.piers) &&
                Objects.equals(piersSemaphore, port.piersSemaphore);
    }

    @Override
    public int hashCode() {
        int result = cargo.hashCode();
        result = 31 * result + piers.hashCode();
        result = 31 * result + piersSemaphore.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "cargo=" + cargo +
                ", piers=" + piers +
                ", piersSemaphore=" + piersSemaphore +
                '}';
    }
}
