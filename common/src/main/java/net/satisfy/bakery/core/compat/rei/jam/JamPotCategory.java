package net.satisfy.bakery.core.compat.rei.jam;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import net.satisfy.bakery.core.registry.ObjectRegistry;

import java.util.ArrayList;
import java.util.List;

public class JamPotCategory implements DisplayCategory<JamPotDisplay> {
    @Override
    public CategoryIdentifier<JamPotDisplay> getCategoryIdentifier() {
        return JamPotDisplay.JAM_POT_DISPLAY;
    }

    @Override
    public Component getTitle() {
        return ObjectRegistry.SMALL_COOKING_POT_ITEM.get().getDescription();
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ObjectRegistry.SMALL_COOKING_POT_ITEM.get());
    }

    @Override
    public int getDisplayHeight() {
        return 36;
    }

    @Override
    public List<Widget> setupDisplay(JamPotDisplay display, Rectangle bounds) {
        Point start = new Point(bounds.getCenterX() - 60, bounds.getCenterY() - 8);
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        for (int i = 0; i < JamPotDisplay.INPUTS; i++) {
            widgets.add(Widgets.createSlot(new Point(start.x + i * 18, start.y))
                    .entries(display.getInputEntries().get(i))
                    .markInput());
        }
        widgets.add(Widgets.createArrow(new Point(start.x + 60, start.y)).animationDurationTicks(display.getCookingTime()));
        widgets.add(Widgets.createResultSlotBackground(new Point(start.x + 98, start.y)));
        widgets.add(Widgets.createSlot(new Point(start.x + 98, start.y))
                .entries(display.getOutputEntries().get(0))
                .disableBackground()
                .markOutput());
        return widgets;
    }
}
