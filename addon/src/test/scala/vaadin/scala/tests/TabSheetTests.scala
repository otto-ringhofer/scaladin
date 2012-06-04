package vaadin.scala.tests

import vaadin.scala._
import org.scalatest.FunSuite
import com.vaadin.terminal.Sizeable
import org.scalatest.BeforeAndAfter
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito
import vaadin.scala.mixins.TabSheetMixin
import vaadin.scala.mixins.TabSheetMixin

class TabSheetTests extends FunSuite with BeforeAndAfter with MockitoSugar {

  class VaadinTabSheet extends com.vaadin.ui.TabSheet with TabSheetMixin

  var tabSheet: TabSheet = _

  val label = new Label
  var tab: TabSheet.Tab = _

  val label2 = new Label
  var tab2: TabSheet.Tab = _

  var spy: VaadinTabSheet = _
  var tabSheetWithSpy: TabSheet = _

  before {
    tabSheet = new TabSheet
    tab = tabSheet.addTab(label)
    tab2 = tabSheet.addTab(label2)

    spy = Mockito.spy(new VaadinTabSheet)
    tabSheetWithSpy = new TabSheet(spy)
  }

  test("Tab.visible") {
    assert(tab.visible)

    tab.visible = false
    assert(!tab.visible)
  }

  test("Tab.closable") {
    assert(!tab.closable)

    tab.closable = true
    assert(tab.closable)
  }

  test("Tab.enabled") {
    assert(tab.enabled)

    tab.enabled = false
    assert(!tab.enabled)
  }

  test("Tab.caption") {
    assert(tab.caption === Some(""))

    tab.caption = "caption"
    assert(tab.caption === Some("caption"))

    tab.caption = None
    assert(tab.caption === None)

    tab.caption = Some("caption")
    assert(tab.caption === Some("caption"))
  }

  test("Tab.icon") {
    assert(tab.icon === None)

    val icon = new ThemeResource("pic.png")

    tab.icon = icon
    assert(tab.icon === Some(icon))

    tab.icon = None
    assert(tab.icon === None)

    tab.icon = Some(icon)
    assert(tab.icon === Some(icon))
  }

  test("Tab.description") {
    assert(tab.description === None)

    tab.description = "description"
    assert(tab.description === Some("description"))

    tab.description = None
    assert(tab.description === None)

    tab.description = Some("description")
    assert(tab.description === Some("description"))
  }

  test("Tab.component") {
    assert(tab.component === label)
  }

  test("Tab.styleName") {
    assert(tab.styleName === None)

    tab.styleName = "styleName"
    assert(tab.styleName === Some("styleName"))

    tab.styleName = None
    assert(tab.styleName === None)

    tab.styleName = Some("styleName")
    assert(tab.styleName === Some("styleName"))
  }

  test("removeTab") {
    tabSheetWithSpy.removeTab(tab)
    Mockito.verify(spy).removeTab(tab.p)
  }

  test("addTab(component)") {
    val labelToBeAdded = new Label

    assert(tabSheetWithSpy.tabs.size === 0)
    val addedTab: TabSheet.Tab = tabSheetWithSpy.addTab(labelToBeAdded)
    Mockito.verify(spy).addTab(labelToBeAdded.p)
    assert(tabSheetWithSpy.tabs.size === 1)
    assert(tabSheetWithSpy.tabs.contains(addedTab.p))
    assert(tabSheetWithSpy.tabs.get(addedTab.p) === Some(addedTab))
  }

  test("addTab(component, caption)") {
    val labelToBeAdded = new Label

    assert(tabSheetWithSpy.tabs.size === 0)
    val addedTab: TabSheet.Tab = tabSheetWithSpy.addTab(labelToBeAdded, "my caption")
    Mockito.verify(spy).addTab(labelToBeAdded.p, "my caption")
    assert(tabSheetWithSpy.tabs.size === 1)
    assert(tabSheetWithSpy.tabs.contains(addedTab.p))
    assert(tabSheetWithSpy.tabs.get(addedTab.p) === Some(addedTab))
  }

  test("addTab(component, caption, icon)") {
    val labelToBeAdded = new Label
    val icon = new ThemeResource("icon.png")

    assert(tabSheetWithSpy.tabs.size === 0)
    val addedTab: TabSheet.Tab = tabSheetWithSpy.addTab(labelToBeAdded, "my caption", icon)
    Mockito.verify(spy).addTab(labelToBeAdded.p, "my caption", icon.p)
    assert(tabSheetWithSpy.tabs.size === 1)
    assert(tabSheetWithSpy.tabs.contains(addedTab.p))
    assert(tabSheetWithSpy.tabs.get(addedTab.p) === Some(addedTab))
  }

  test("addTab(component, caption, icon, position)") {
    val labelToBeAdded = new Label
    val icon = new ThemeResource("icon.png")

    assert(tabSheetWithSpy.tabs.size === 0)
    val addedTab: TabSheet.Tab = tabSheetWithSpy.addTab(labelToBeAdded, "my caption", icon, 0)
    Mockito.verify(spy).addTab(labelToBeAdded.p, "my caption", icon.p, 0)
    assert(tabSheetWithSpy.tabs.size === 1)
    assert(tabSheetWithSpy.tabs.contains(addedTab.p))
    assert(tabSheetWithSpy.tabs.get(addedTab.p) === Some(addedTab))
  }

  test("addTab(component, position)") {
    val labelToBeAdded = new Label
    
    assert(tabSheetWithSpy.tabs.size === 0)
    val addedTab: TabSheet.Tab = tabSheetWithSpy.addTab(labelToBeAdded, 0)
    Mockito.verify(spy).addTab(labelToBeAdded.p, 0)
    assert(tabSheetWithSpy.tabs.size === 1)
    assert(tabSheetWithSpy.tabs.contains(addedTab.p))
    assert(tabSheetWithSpy.tabs.get(addedTab.p) === Some(addedTab))
  }
  
  ignore("addComponent") {
    val labelToBeAdded = new Label
    
    tabSheet.components += label
    assert(tabSheet.tab(labelToBeAdded) != None)
  }

  test("tabsVisible") {
    assert(tabSheet.tabsVisible)

    tabSheet.tabsVisible = false
    assert(!tabSheet.tabsVisible)
  }

  test("tab") {
    assert(tabSheet.tab(0) === Some(tab))
  }

  test("tab, illegal position returns None") {
    assert(tabSheet.tab(2) === None)
  }

  test("selectedComponent") {
    assert(tabSheet.selectedComponent === label)

    tabSheet.selectedComponent = label2
    assert(tabSheet.selectedComponent === label2)
  }

  test("selectedComponent, selecting uncontained component") {
    // nothing happens, silently ignored, should an exception be thrown?
    tabSheet.selectedComponent = new Label

    assert(tabSheet.selectedComponent === label)
  }

  test("selectedTab") {
    assert(tabSheet.selectedTab === tab)

    tabSheet.selectedTab = tab2
    assert(tabSheet.selectedTab === tab2)
  }

  test("selectedTab, selecting a tab from another TabSheet") {
    val tabFromAnotherTabSheet = new TabSheet().addTab(new Label)

    // nothing happens, silently ignored, should an exception be thrown?
    tabSheet.selectedTab = tabFromAnotherTabSheet

    assert(tabSheet.selectedTab === tab)
  }

  test("tabPosition") {
    assert(tabSheet.tabPosition(tab) === 0)
    assert(tabSheet.tabPosition(tab2) === 1)

    tabSheet.tabPosition(tab, 1)

    assert(tabSheet.tabPosition(tab) === 1)
    assert(tabSheet.tabPosition(tab2) === 0)
  }

  test("tabPosition, position for a Tab from another TabSheet") {
    val tabFromAnotherTabSheet = new TabSheet().addTab(new Label)

    assert(tabSheet.tabPosition(tabFromAnotherTabSheet) === -1)
  }

  test("closeHandler") {
    assert(tabSheet.closeHandler != None)

    val myCloseHandler = (e: TabSheet.TabCloseEvent) => println(e.tabSheet);
    tabSheet.closeHandler = myCloseHandler
    assert(tabSheet.closeHandler === myCloseHandler)

  }

  test("Example") {
    val tabSheet = new TabSheet {
      sizeFull()
      closeHandler = (e: TabSheet.TabCloseEvent) => {
        // do nothing: pressing the x button doesn't close the tab
      }
      val tab1 = addTab(new Label, "Tab 1")
      tab1.closable = true
      val tab2 = addTab(new Label, "Tab 2", new ThemeResource("../runo/icons/16/globe.png"))
      tab2.closable = true

      selectedTab = tab2
    }
  }
}