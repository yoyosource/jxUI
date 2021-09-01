# jxUI

## Status

- [x] Stacks
    - [x] VStacks
    - [x] HStacks
    - [x] ZStacks
- [ ] Text
    - [x] Color
    - [x] Size
    - [x] Font
    - [ ] Shadow?
- [ ] Button
    - [ ] Groups
- [ ] Images
    - [ ] Corner Radius
- [x] Spacer
    - [x] size
- [ ] List
- [ ] Object watching
- [ ] Gradient -> Color

## Draw

- Calc Size of each node
- Set Spacer size from layer to layer in percentage of drawState
- Draw

## Draw rework

- Draw State (User State of IF's and LIST's)
- Calculating size with a State and the Size of the Canvas
    - (Get size of current component)
    - Stack
        - HStack, calc current size, give spacer of current HStack the size they can have and cascade
        - VStack, calc current size, give spacer of current VStack the size they can have and cascade
        - ZStack, give every enclosing component the same space it gets from the parent

```java
import java.awt.*;

public interface Component {
    // UserState saves the state of the user primarily a Map of Object to Object mapping
    Size size(UserState userState); // Just the current size of the component with every sub component if needed

    // DrawState saves the Size object to every Component for the draw() call later on
    void size(Size size, UserState userState, DrawState drawState); // Calculate Spacer and Divider sizing of current component and every sub component if needed

    // Count spacers of current and deeper Objects
    default int spacers(Orientation orientation) {
        return 0;
    }

    void draw(Graphics2D g, DrawState drawState, Point current); // Draw the current component to the Screen
}
```