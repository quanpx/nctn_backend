package quanphung.hust.nctnbackend.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableAsync
public class AsyncTaskConfig
{

  public static final String TASK_EXECUTOR_BEAN = "taskExecutor";

  @Autowired
  private Environment env;

  private int corePoolSize;

  private int maxPoolSize;

  private int queueCapacity;

  @PostConstruct
  public void init()
  {
    corePoolSize = env.getProperty("application.task.corePoolSize", Integer.class, 25);
    maxPoolSize = env.getProperty("application.task.maxPoolSize", Integer.class, Integer.MAX_VALUE);
    queueCapacity = env.getProperty("application.task.queueCapacity", Integer.class, Integer.MAX_VALUE);
  }

  @Bean(name = TASK_EXECUTOR_BEAN)
  public AsyncTaskExecutor taskExecutor()
  {
    ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
    pool.setCorePoolSize(corePoolSize);
    pool.setMaxPoolSize(maxPoolSize);
    pool.setQueueCapacity(queueCapacity);
    pool.setWaitForTasksToCompleteOnShutdown(true);
    pool.setThreadNamePrefix("task-");

    return pool;
  }

  @Bean
  public TaskScheduler poolScheduler() {

    ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();

    scheduler.setPoolSize(10);
    scheduler.setThreadNamePrefix("scheduler_");

    return scheduler;
  }


}
