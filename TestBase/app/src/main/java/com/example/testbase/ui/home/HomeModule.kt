package  com.example.testbase.ui.home

import com.example.testbase.ui.home.task.TaskFragment
import com.example.testbase.ui.home.today_task.TodayTaskFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class HomeModule {

    @ContributesAndroidInjector
   internal abstract fun contributesTodayTaskFragment() : TodayTaskFragment

    @ContributesAndroidInjector
    internal abstract fun contributesTaskFragment() : TaskFragment

}