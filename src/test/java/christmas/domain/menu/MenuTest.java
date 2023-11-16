package christmas.domain.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuTest {

    @DisplayName("검색한 이름과 일치하는 메뉴를 찾을 수 있다.")
    @Test
    void findByNameTest() {
        assertThat(Menu.findByName("티본스테이크")).isEqualTo(Menu.T_BONE_STEAK);
        assertThat(Menu.findByName("시저샐러드")).isEqualTo(Menu.CAESAR_SALAD);
        assertThat(Menu.findByName("제로콜라")).isEqualTo(Menu.ZERO_COLA);
    }

    @DisplayName("존재하지 않는 메뉴를 검색하면 예외를 발생시킨다.")
    @Test
    void FindByNameFailedTest() {
        assertThatThrownBy(() -> Menu.findByName("봉골레파스타"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

    @DisplayName("해당 메뉴의 카테고리를 반환한다.")
    @Test
    void getCategoryTest() {
        assertThat(Menu.MUSHROOM_SOUP.getMenuCategory()).isEqualTo(MenuCategory.APPETIZER);
        assertThat(Menu.T_BONE_STEAK.getMenuCategory()).isEqualTo(MenuCategory.MAIN);
        assertThat(Menu.ZERO_COLA.getMenuCategory()).isEqualTo(MenuCategory.DRINK);
        assertThat(Menu.ICE_CREAM.getMenuCategory()).isEqualTo(MenuCategory.DESSERT);
    }

}