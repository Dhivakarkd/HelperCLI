package com.dhivakar.helpercli.commands;

import org.springframework.shell.component.MultiItemSelector;
import org.springframework.shell.component.PathInput;
import org.springframework.shell.component.PathSearch;
import org.springframework.shell.component.support.Itemable;
import org.springframework.shell.component.support.SelectorItem;
import org.springframework.shell.standard.AbstractShellComponent;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class HelloCommand extends AbstractShellComponent {

    @ShellMethod(key = "component path input", value = "Path input", group = "Components")
    public String pathInput() {
        PathSearch.PathSearchConfig config = new PathSearch.PathSearchConfig();
        config.setMaxPathsShow(5);
        config.setMaxPathsSearch(100);
        config.setSearchForward(true);
        config.setSearchCaseSensitive(false);
        config.setSearchNormalize(false);

        PathSearch component = new PathSearch(getTerminal(), "Enter value", config);
        component.setResourceLoader(getResourceLoader());
        component.setTemplateExecutor(getTemplateExecutor());

        PathSearch.PathSearchContext context = component.run(PathSearch.PathSearchContext.empty());
        return "Got value " + context.getResultValue();
    }

    @ShellComponent
    public class ComponentCommands extends AbstractShellComponent {

        @ShellMethod(key = "multi", value = "Multi selector", group = "Components")
        public String multiSelector() {
            List<SelectorItem<String>> items = new ArrayList<>();
            items.add(SelectorItem.of("key1", "value1"));
            items.add(SelectorItem.of("key2", "value2", false, true));
            items.add(SelectorItem.of("key3", "value3"));
            MultiItemSelector<String, SelectorItem<String>> component = new MultiItemSelector<>(getTerminal(),
                    items, "testSimple", null);
            component.setResourceLoader(getResourceLoader());
            component.setTemplateExecutor(getTemplateExecutor());
            MultiItemSelector.MultiItemSelectorContext<String, SelectorItem<String>> context = component
                    .run(MultiItemSelector.MultiItemSelectorContext.empty());
            String result = context.getResultItems().stream()
                    .map(Itemable::getItem)
                    .collect(Collectors.joining(","));
            return "Got value " + result;
        }
    }
}
