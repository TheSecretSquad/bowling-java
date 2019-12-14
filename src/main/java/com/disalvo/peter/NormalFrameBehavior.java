package com.disalvo.peter;

public class NormalFrameBehavior implements FrameBehavior {
    @Override
    public void strike(DefaultFrame frame) {
        frame.complete();
        frame.requestBonusRolls(2);
    }

    @Override
    public void spare(DefaultFrame frame) {
        frame.complete();
        frame.requestBonusRolls(1);
    }
}
