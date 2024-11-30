import subprocess
import logging
import http.server
import socketserver
import os

logging.basicConfig(
    filename="terminal_log.txt",
    level=logging.INFO,
    format="%(asctime)s - %(levelname)s - %(message)s",
    filemode="w"
)
PORT = 8000

def run_command(command):
    try:
        logging.info(f"Running command: {command}")

        result = subprocess.run(
            command,
            shell=True,
            text=True,
            capture_output=True
        )

        if result.stdout:
            logging.info(f"Output: {result.stdout.strip()}")
        if result.stderr:
            logging.error(f"Error: {result.stderr.strip()}")

        return result.stdout.strip() if result.stdout else result.stderr.strip()
    except Exception as e:
        error_message = f"Exception occurred: {e}"
        logging.error(error_message)
        return error_message


class MyHandler(http.server.SimpleHTTPRequestHandler):
    def do_GET(self):
        if self.path == '/':
            self.path = 'index.html'
        return http.server.SimpleHTTPRequestHandler.do_GET(self)

if __name__ == "__main__":
    try:
        RED = "\033[31m"
        RESET = "\033[0m"
        print(RED + "You need to have Ganache running and connected to the Metamask. Without this prerequisite application won't work properly" + RESET)

        run_command("truffle compile")
        run_command("truffle migrate --reset")

        print("Truffle deployed successfully")

        # os.chdir(os.path.dirname(os.path.abspath(__file__)))

        with socketserver.TCPServer(("", PORT), MyHandler) as httpd:
            print(f"Serving at port {PORT}")
            httpd.serve_forever()
    except Exception as e:
        print("There was an error check terminal_log.txt")