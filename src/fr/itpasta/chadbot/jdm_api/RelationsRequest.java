package fr.itpasta.chadbot.jdm_api;

import fr.itpasta.chadbot.generic._2Uplet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static fr.itpasta.chadbot.Main.api;

public final class RelationsRequest {
    private static final long MAX_WAIT = 5000;

    public static List<LexicalGraph> ask(int nThreads, List<String> words) {
        List<LexicalGraph> result = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        List<Future<_2Uplet<LexicalGraph, Integer>>> futures = new ArrayList<>();
        int serverWeight = 0;
        while(!words.isEmpty()) {
            for (int i = 0; i < nThreads && !words.isEmpty(); i++) {
                Future<_2Uplet<LexicalGraph, Integer>> future = executorService.submit(new RelationsRequestThread(words.get(0)));
                words.remove(0);
                futures.add(future);
            }
            for (Future<_2Uplet<LexicalGraph, Integer>> future : futures) {
                try {
                    _2Uplet<LexicalGraph, Integer> futureResult = future.get();
                    result.add(futureResult.getO1());
                    serverWeight += futureResult.getO2();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            futures.clear();
            try {
                Thread.sleep(serverWeight * (MAX_WAIT/nThreads));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            serverWeight = 0;
        }

        executorService.shutdown();
        return result;
    }

    public static LexicalGraph ask(String word) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<_2Uplet<LexicalGraph, Integer> > future = executorService.submit(new RelationsRequestThread(word));
        _2Uplet<LexicalGraph, Integer> result = null;
        try {
            result = future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        executorService.shutdown();
        return result.getO1();
    }

    public static class RelationsRequestThread implements Callable<_2Uplet<LexicalGraph, Integer> > {
        private String word;

        public RelationsRequestThread(String word) {
            this.word = word;
        }

        @Override
        public _2Uplet<LexicalGraph, Integer> call() throws Exception {
            return api.getLexicalGraph(word);
        }
    }
}
