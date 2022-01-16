import SwiftUI
import common
import common_client

struct ContentView: View {
	var body: some View {
        TabView {
            HomeView()
                    .tabItem {
                        Image(systemName: "house.fill")
                        Text("홈")
                    }
            
            NotificationView()
                .tabItem {
                    Image(systemName: "notification.fill")
                    Text("알림")
                }
            
            MenuView()
                .tabItem {
                    Image(systemName: "menu.fill")
                    Text("메뉴")
                }
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
	ContentView()
	}
}
