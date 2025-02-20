const NotFound = () => {
    return (
      <div className="flex flex-col items-center justify-center h-screen text-center">
        <h1 className="text-4xl font-bold text-red-600">404</h1>
        <p className="text-xl text-gray-700 mt-2">Page Not Found</p>
        <a href="/home" className="mt-4 px-4 py-2 bg-blue-500 text-white rounded-md">
          Go to Home
        </a>
      </div>
    );
  };
  
  export default NotFound;
  