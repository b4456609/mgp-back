package soselab.mpg.graph.service;

import java.util.concurrent.Future;

/**
 * Created by Fan on 2017/1/25.
 */
public interface MicroserviceGraphBuilderService {
    Future<Boolean> build();
}
