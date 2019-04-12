import Badge from "../components/Base/Badge";
import BaseAlert from "../components/Base/BaseAlert";
import BaseButton from "../components/Base/BaseButton";
import BaseCheckbox from "../components/Base/BaseCheckbox";
import BaseInput from "../components/Base/BaseInput";
import BasePagination from "../components/Base/BasePagination";
import BaseProgress from "../components/Base/BaseProgress";
import BaseRadio from "../components/Base/BaseRadio";
import BaseSlider from "../components/Base/BaseSlider";
import BaseSwitch from "../components/Base/BaseSwitch";
import Card from "../components/Base/Card";
import Icon from "../components/Base/Icon";
import Header from "../components/Header"
import Sidebar from "../components/Sidebar"

export default {
  install(Vue) {
    Vue.component(Badge.name, Badge);
    Vue.component(BaseAlert.name, BaseAlert);
    Vue.component(BaseButton.name, BaseButton);
    Vue.component(BaseInput.name, BaseInput);
    Vue.component(BaseCheckbox.name, BaseCheckbox);
    Vue.component(BasePagination.name, BasePagination);
    Vue.component(BaseProgress.name, BaseProgress);
    Vue.component(BaseRadio.name, BaseRadio);
    Vue.component(BaseSlider.name, BaseSlider);
    Vue.component(BaseSwitch.name, BaseSwitch);
    Vue.component(Card.name, Card);
    Vue.component(Icon.name, Icon);
    Vue.component(Header.name, Header);
    Vue.component(Sidebar.name, Sidebar);
  }
};
