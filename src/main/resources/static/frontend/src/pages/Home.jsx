// src/pages/Home.jsx
import HomeHeader from "../components/HomeHeader";
import Footer from "../components/Footer";

const Home = () => {
  return (
    <div className="flex flex-col min-h-screen">
      <HomeHeader />
      <main className="flex-grow flex items-center justify-center text-center p-6">
        <div>
          <h1 className="text-3xl font-bold mb-4">Welcome to EffiGo EMS</h1>
          <p>
            Efficiently manage employees, roles, and sessions with our system.
          </p>
        </div>
      </main>
      <Footer />
    </div>
  );
};

export default Home;
