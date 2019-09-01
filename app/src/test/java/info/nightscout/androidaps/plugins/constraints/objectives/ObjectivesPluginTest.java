package info.nightscout.androidaps.plugins.constraints.objectives;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Date;

import info.AAPSMocker;
import info.nightscout.androidaps.MainApp;
import info.nightscout.androidaps.interfaces.Constraint;
import info.nightscout.androidaps.plugins.configBuilder.ConfigBuilderPlugin;
import info.nightscout.androidaps.utils.SP;

/**
 * Created by mike on 23.03.2018.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({MainApp.class, ConfigBuilderPlugin.class, SP.class})
public class ObjectivesPluginTest {

    ObjectivesPlugin objectivesPlugin;

    @Test
    public void notStartedObjectivesShouldLimitLoopInvocation() throws Exception {
        objectivesPlugin.objectives.get(ObjectivesPlugin.FIRST_OBJECTIVE).setStartedOn(null);

        Constraint<Boolean> c = new Constraint<>(true);
        c = objectivesPlugin.isLoopInvocationAllowed(c);
        Assert.assertEquals("Objectives: Objective 1 not started", c.getReasons());
        Assert.assertEquals(Boolean.FALSE, c.value());
        objectivesPlugin.objectives.get(ObjectivesPlugin.FIRST_OBJECTIVE).setStartedOn(new Date());
    }

    @Test
    public void notStartedObjective4ShouldLimitClosedLoop() throws Exception {
        objectivesPlugin.objectives.get(ObjectivesPlugin.CLOSED_LOOP_OBJECTIVE).setStartedOn(null);

        Constraint<Boolean> c = new Constraint<>(true);
        c = objectivesPlugin.isClosedLoopAllowed(c);
        Assert.assertEquals(true, c.getReasons().contains("Objective 4 not started"));
        Assert.assertEquals(Boolean.FALSE, c.value());
    }

    @Test
    public void notStartedObjective6ShouldLimitAutosensMode() throws Exception {
        objectivesPlugin.objectives.get(ObjectivesPlugin.AUTOSENS_OBJECTIVE).setStartedOn(null);

        Constraint<Boolean> c = new Constraint<>(true);
        c = objectivesPlugin.isAutosensModeEnabled(c);
        Assert.assertEquals(true, c.getReasons().contains("Objective 6 not started"));
        Assert.assertEquals(Boolean.FALSE, c.value());
    }

    @Test
    public void notStartedObjective7ShouldLimitAMAMode() throws Exception {
        objectivesPlugin.objectives.get(ObjectivesPlugin.AMA_OBJECTIVE).setStartedOn(null);

        Constraint<Boolean> c = new Constraint<>(true);
        c = objectivesPlugin.isAMAModeEnabled(c);
        Assert.assertEquals(true, c.getReasons().contains("Objective 7 not started"));
        Assert.assertEquals(Boolean.FALSE, c.value());
    }

    @Test
    public void notStartedObjective8ShouldLimitSMBMode() throws Exception {
        objectivesPlugin.objectives.get(ObjectivesPlugin.SMB_OBJECTIVE).setStartedOn(null);

        Constraint<Boolean> c = new Constraint<>(true);
        c = objectivesPlugin.isSMBModeEnabled(c);
        Assert.assertEquals(true, c.getReasons().contains("Objective 8 not started"));
        Assert.assertEquals(Boolean.FALSE, c.value());
    }

    @Before
    public void prepareMock() {
        AAPSMocker.mockMainApp();
        AAPSMocker.mockConfigBuilder();
        AAPSMocker.mockBus();
        AAPSMocker.mockSP();
        AAPSMocker.mockStrings();

        objectivesPlugin = ObjectivesPlugin.getPlugin();
    }
}
