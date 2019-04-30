import Badge from "../components/base_components/Badge";
import BaseAlert from "../components/base_components/BaseAlert";
import BaseButton from "../components/base_components/BaseButton";
import BaseCheckbox from "../components/base_components/BaseCheckbox";
import BaseInput from "../components/base_components/BaseInput";
import BasePagination from "../components/base_components/BasePagination";
import BaseProgress from "../components/base_components/BaseProgress";
import BaseRadio from "../components/base_components/BaseRadio";
import BaseSlider from "../components/base_components/BaseSlider";
import BaseSwitch from "../components/base_components/BaseSwitch";
import Card from "../components/base_components/Card";
import Icon from "../components/base_components/Icon";
import Header from "../components/header/Header"

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
  }
};
