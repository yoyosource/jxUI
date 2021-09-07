package de.jxui.components;

class SpacerCalculation {

    private float delta;
    private float current = 0;

    public SpacerCalculation(int size, int count) {
        if (count == 0) {
            delta = 0;
        } else {
            this.delta = size / (float) count;
        }
    }

    public int next() {
        float temp = current;
        current += delta;
        return (int) current - (int) temp;
    }
}
